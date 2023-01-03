package com.leoleo.androidgithubsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.data.api.GithubApi
import com.leoleo.androidgithubsearch.data.api.GithubApi.Companion.SEARCH_PER_PAGE
import com.leoleo.androidgithubsearch.data.api.KtorHandler
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import com.leoleo.androidgithubsearch.data.paging.GithubRepoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val ktorHandler: KtorHandler,
) : com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository {

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): com.leoleo.androidgithubsearch.domain.model.RepositoryDetail =
        ktorHandler.dataOrThrow { api.fetchRepositoryDetail(ownerName, repositoryName) }

    override fun searchRepositories(query: String): Flow<PagingData<com.leoleo.androidgithubsearch.domain.model.RepositorySummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = SEARCH_PER_PAGE,
                initialLoadSize = SEARCH_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GithubRepoPagingSource(query, api, ktorHandler) },
        ).flow
    }
}