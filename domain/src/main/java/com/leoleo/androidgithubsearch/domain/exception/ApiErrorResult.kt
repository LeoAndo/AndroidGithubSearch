package com.leoleo.androidgithubsearch.domain.exception

sealed class ApiErrorResult : Exception() {
    data class UnAuthorizedError(override val message: String) : ApiErrorResult()
    data class ForbiddenError(override val message: String) : ApiErrorResult()
    object NetworkError : ApiErrorResult()
    data class UnprocessableEntity(override val message: String) : ApiErrorResult()
    data class UnexpectedError(override val message: String) : ApiErrorResult()
    data class NotFoundError(override val message: String) : ApiErrorResult()
}