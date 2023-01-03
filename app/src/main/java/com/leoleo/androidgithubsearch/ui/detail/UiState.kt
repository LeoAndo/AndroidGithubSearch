package com.leoleo.androidgithubsearch.ui.detail

import com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail

sealed interface UiState {
    object Initial : UiState
    object Loading : UiState
    data class Data(val repositoryDetail: com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail) : UiState
    data class Error(val throwable: Throwable) : UiState
}

