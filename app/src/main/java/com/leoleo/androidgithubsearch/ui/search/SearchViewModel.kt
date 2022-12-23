package com.leoleo.androidgithubsearch.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GithubRepoRepository,
) : ViewModel() {
    var uiState by mutableStateOf<SearchUiState>(SearchUiState.Initial)

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            uiState = SearchUiState.Error(throwable.localizedMessage ?: "error")
        }

    fun searchRepositories(query: String, page: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            if (query.isEmpty()) {
                uiState = SearchUiState.Error("Please input repository name")
                return@launch
            }
            uiState = SearchUiState.Loading
            val result = repository.searchRepositories(query, 1)
            uiState = if (result.isNotEmpty()) {
                SearchUiState.Data(result)
            } else {
                SearchUiState.Empty
            }
        }
    }
}