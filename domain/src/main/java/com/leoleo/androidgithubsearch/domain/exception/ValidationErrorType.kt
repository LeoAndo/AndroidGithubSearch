package com.leoleo.androidgithubsearch.domain.exception

/**
 * 検査の例外用クラス
 */
sealed class ValidationErrorType : Exception() {
    data class Input(override val message: String?) : ValidationErrorType()
}