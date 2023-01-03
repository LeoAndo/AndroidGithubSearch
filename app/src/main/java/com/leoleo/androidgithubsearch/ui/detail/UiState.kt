package com.leoleo.androidgithubsearch.ui.detail

import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail

sealed interface UiState {
    object Initial : UiState
    object Loading : UiState
    data class Data(val repositoryDetail: RepositoryDetail) : UiState
    data class Error(val throwable: Throwable) : UiState
}

