package com.leoleo.androidgithubsearch.domain.exception

/**
 * API関連のエラータイプ
 */
sealed class ApiErrorType : Exception() {
    data class UnAuthorizedError(override val message: String) : ApiErrorType()
    data class ForbiddenError(override val message: String) : ApiErrorType()
    object NetworkError : ApiErrorType()
    data class UnprocessableEntity(override val message: String) : ApiErrorType()
    data class UnexpectedError(override val message: String) : ApiErrorType()
    data class NotFoundError(override val message: String) : ApiErrorType()
}