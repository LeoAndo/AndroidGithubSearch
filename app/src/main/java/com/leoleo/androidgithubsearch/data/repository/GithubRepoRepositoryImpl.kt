package com.leoleo.androidgithubsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.GithubService.Companion.SEARCH_PER_PAGE
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.data.dataOrThrow
import com.leoleo.androidgithubsearch.data.paging.GithubRepoPagingSource
import com.leoleo.androidgithubsearch.di.IoDispatcher
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val api: GithubService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GithubRepoRepository {

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail =
        dataOrThrow(dispatcher) { api.fetchRepositoryDetail(ownerName, repositoryName).toModel() }

    override fun searchRepositories(query: String): Flow<PagingData<RepositorySummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = SEARCH_PER_PAGE,
                initialLoadSize = SEARCH_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GithubRepoPagingSource(query, api, dispatcher) },
        ).flow
    }
}