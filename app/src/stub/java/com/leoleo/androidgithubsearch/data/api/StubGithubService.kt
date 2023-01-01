package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary

class StubGithubService: GithubService {
    override suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<RepositorySummary> {
        throw ApiErrorResult.UnexpectedError("stub1")
    }

    override suspend fun fetchRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail {
        throw ApiErrorResult.UnexpectedError("stub2")
    }

}