package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.domain.exception.ApiErrorType
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import java.net.UnknownHostException

internal class KtorHandler {
    /**
     * 共通のAPI エラーハンドリングはここで行う.
     */
    @Throws(ApiErrorType::class)
    fun handleResponseException(e: Throwable) {
        when (e) {
            is UnknownHostException, is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException -> {
                throw ApiErrorType.Network
            }
            // ktor: 300番台のエラー
            is RedirectResponseException -> throw ApiErrorType.Redirect
            // ktor: 500番台のエラー
            is ServerResponseException -> throw ApiErrorType.Server
            // ktor: それ以外のエラー
            is ResponseException -> throw ApiErrorType.Unknown(e.localizedMessage)
            else -> throw ApiErrorType.Unknown(e.localizedMessage)
        }
    }
}