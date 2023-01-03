package com.leoleo.androidgithubsearch.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoleo.androidgithubsearch.data.domain.repository.GithubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: com.leoleo.androidgithubsearch.data.domain.repository.GithubRepoRepository
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Initial)
        private set
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiState = UiState.Error(throwable)
    }

    fun getRepositoryDetail(ownerName: String, repositoryName: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            uiState = UiState.Loading
            uiState = UiState.Data(repository.getRepositoryDetail(ownerName, repositoryName))
        }
    }
}
