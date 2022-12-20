package com.leoleo.androidgithubsearch.data.repository

import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.data.dataOrThrow
import com.leoleo.androidgithubsearch.di.IoDispatcher
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher

class GithubRepositoryImpl(
    private val api: GithubService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GithubRepository {
    override suspend fun searchRepositories(query: String, page: Int): List<RepositorySummary> =
        dataOrThrow(dispatcher) { api.searchRepositories(query, page).toModel() }

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail =
        dataOrThrow(dispatcher) { api.fetchRepositoryDetail(ownerName, repositoryName).toModel() }
}