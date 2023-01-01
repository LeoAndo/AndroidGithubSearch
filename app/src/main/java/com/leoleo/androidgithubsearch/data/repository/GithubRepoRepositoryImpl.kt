package com.leoleo.androidgithubsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.GithubService.Companion.SEARCH_PER_PAGE
import com.leoleo.androidgithubsearch.data.api.KtorHandler
import com.leoleo.androidgithubsearch.data.paging.GithubRepoPagingSource
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val api: GithubService,
    private val ktorHandler: KtorHandler,
) : GithubRepoRepository {

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail =
        ktorHandler.dataOrThrow { api.fetchRepositoryDetail(ownerName, repositoryName) }

    override fun searchRepositories(query: String): Flow<PagingData<RepositorySummary>> {
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