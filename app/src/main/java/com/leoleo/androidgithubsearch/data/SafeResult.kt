package com.leoleo.androidgithubsearch.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Error(val errorResult: ErrorResult) : SafeResult<Nothing>()
}

sealed class ErrorResult : Exception() {
    data class UnAuthorizedError(override val message: String? = "UnAuthorizedError") :
        ErrorResult()

    data class BadRequestError(override val message: String? = "BadRequestError") :
        ErrorResult()

    data class NotFoundError(override val message: String? = "NotFoundError") :
        ErrorResult()

    data class NetworkError(override val message: String? = "NetworkError") :
        ErrorResult()

    data class UnexpectedError(override val message: String? = "UnexpectedError") :
        ErrorResult()
}

suspend fun <T> dataOrThrow(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): T {
    return withContext(dispatcher) {
        return@withContext when (val result = safeCall(dispatcher) { apiCall.invoke() }) {
            is SafeResult.Error -> throw  result.errorResult
            is SafeResult.Success -> result.data
        }
    }
}

private suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): SafeResult<T> {
    return withContext(dispatcher) {
        try {
            SafeResult.Success(apiCall.invoke())
        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> SafeResult.Error(
                            ErrorResult.UnAuthorizedError(
                                e.localizedMessage
                            )
                        )
                        HttpURLConnection.HTTP_BAD_REQUEST -> SafeResult.Error(
                            ErrorResult.BadRequestError(
                                e.localizedMessage
                            )
                        )
                        HttpURLConnection.HTTP_NOT_FOUND -> SafeResult.Error(
                            ErrorResult.NotFoundError(
                                e.localizedMessage
                            )
                        )
                        else -> SafeResult.Error(ErrorResult.UnexpectedError(e.localizedMessage))
                    }
                }
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    SafeResult.Error(ErrorResult.NetworkError(e.localizedMessage))
                }
                else -> SafeResult.Error(ErrorResult.UnexpectedError(e.localizedMessage))
            }
        }
    }
}