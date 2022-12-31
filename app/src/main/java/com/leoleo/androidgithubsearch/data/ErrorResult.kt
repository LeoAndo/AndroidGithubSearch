package com.leoleo.androidgithubsearch.data

import com.leoleo.androidgithubsearch.data.api.response.GithubErrorResponse
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception
import java.net.UnknownHostException

sealed class ErrorResult : Exception() {
    data class UnAuthorizedError(override val message: String) : ErrorResult()
    data class ForbiddenError(override val message: String) : ErrorResult()
    object NetworkError : ErrorResult()
    data class UnprocessableEntity(override val message: String) : ErrorResult()
    data class UnexpectedError(override val message: String) : ErrorResult()
    data class NotFoundError(override val message: String) : ErrorResult()
}

suspend fun <T> dataOrThrow(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): T {
    return withContext(dispatcher) {
        try {
            apiCall.invoke()
        } catch (e: Throwable) {
            when (e) {
                is UnknownHostException, is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException -> {
                    throw ErrorResult.NetworkError
                }
                // ktor: 300番台のエラー
                is RedirectResponseException -> throw e
                is ClientRequestException -> { // ktor: 400番台のエラー
                    val errorResponse = e.response
                    val format = Json { ignoreUnknownKeys = true }
                    val message =
                        format.decodeFromString<GithubErrorResponse>(errorResponse.body()).message
                    when (errorResponse.status) {
                        HttpStatusCode.Unauthorized -> throw ErrorResult.UnAuthorizedError(message)
                        HttpStatusCode.NotFound -> throw ErrorResult.NotFoundError(message)
                        HttpStatusCode.Forbidden -> throw ErrorResult.ForbiddenError(message)
                        HttpStatusCode.UnprocessableEntity -> {
                            throw ErrorResult.UnprocessableEntity(message)
                        }
                        else -> throw e
                    }
                }
                // ktor: 500番台のエラー
                is ServerResponseException -> throw e
                // ktor: それ以外のエラー
                is ResponseException -> throw e
                else -> throw e
            }
        }
    }
}