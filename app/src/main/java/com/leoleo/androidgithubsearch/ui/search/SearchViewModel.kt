package com.leoleo.androidgithubsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GithubRepoRepository,
) : ViewModel() {
    private val queryCh = Channel<String>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val githubRepositories: Flow<PagingData<RepositorySummary>> =
        queryCh.receiveAsFlow().flatMapLatest {
            repository.searchRepositories(it)
        }.cachedIn(viewModelScope)

    fun searchRepositories(query: String) {
        queryCh.trySend(query)
    }
}