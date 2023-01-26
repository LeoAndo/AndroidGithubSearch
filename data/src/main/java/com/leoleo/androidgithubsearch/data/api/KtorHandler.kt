package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.data.api.response.GithubErrorResponse
import com.leoleo.androidgithubsearch.domain.exception.ApiErrorType
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.UnknownHostException

internal class KtorHandler(
    private val dispatcher: CoroutineDispatcher,
    private val format: Json,
) {
    suspend fun <T> dataOrThrow(apiCall: suspend () -> T): T {
        return withContext(dispatcher) {
            try {
                apiCall.invoke()
            } catch (e: Throwable) {
                when (e) {
                    is UnknownHostException, is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException -> {
                        throw ApiErrorType.NetworkError
                    }
                    // ktor: 300番台のエラー
                    is RedirectResponseException -> throw e
                    is ClientRequestException -> { // ktor: 400番台のエラー
                        val errorResponse = e.response
                        val message =
                            format.decodeFromString<GithubErrorResponse>(errorResponse.body()).message
                        when (errorResponse.status) {
                            HttpStatusCode.Unauthorized -> throw ApiErrorType.UnAuthorizedError(
                                message
                            )
                            HttpStatusCode.NotFound -> throw ApiErrorType.NotFoundError(message)
                            HttpStatusCode.Forbidden -> throw ApiErrorType.ForbiddenError(message)
                            HttpStatusCode.UnprocessableEntity -> {
                                throw ApiErrorType.UnprocessableEntity(message)
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
}