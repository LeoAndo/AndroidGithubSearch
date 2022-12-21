package com.leoleo.androidgithubsearch.data.repository

import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.data.dataOrThrow
import com.leoleo.androidgithubsearch.di.IoDispatcher
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GithubRepoRepositoryImpl @Inject constructor(
    private val api: GithubService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GithubRepoRepository {
    override suspend fun searchRepositories(query: String, page: Int): List<RepositorySummary> =
        dataOrThrow(dispatcher) { api.searchRepositories(query, page).toModel() }

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail =
        dataOrThrow(dispatcher) { api.fetchRepositoryDetail(ownerName, repositoryName).toModel() }
}