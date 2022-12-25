package com.leoleo.androidgithubsearch.ui.search

import com.leoleo.androidgithubsearch.domain.model.RepositorySummary

sealed interface SearchUiState {
    object Initial : SearchUiState
    object Loading : SearchUiState
    object Empty : SearchUiState
    data class Data(val repoList: List<RepositorySummary>) : SearchUiState
    data class Error(val throwable: Throwable) : SearchUiState
}

