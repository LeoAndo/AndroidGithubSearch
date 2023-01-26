package com.leoleo.androidgithubsearch.domain.exception

/**
 * API関連のエラータイプ
 */
sealed class ApiErrorType : Exception() {
    data class UnAuthorized(override val message: String?) : ApiErrorType()
    data class Forbidden(override val message: String?) : ApiErrorType()
    object Network : ApiErrorType()
    data class UnprocessableEntity(override val message: String?) : ApiErrorType()
    data class Unknown(override val message: String?) : ApiErrorType()
    data class NotFound(override val message: String?) : ApiErrorType()
    object Redirect : ApiErrorType()
    object Server : ApiErrorType()
}