package com.leoleo.androidgithubsearch.domain.usecase

import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.domain.di.DefaultDispatcher
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: GithubRepoRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<RepositorySummary>> {
        return withContext(dispatcher) {
            repository.searchRepositories(query)
        }
    }
}