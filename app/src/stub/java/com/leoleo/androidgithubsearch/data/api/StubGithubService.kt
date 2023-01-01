package com.leoleo.androidgithubsearch.data.api

import android.content.Context
import com.leoleo.androidgithubsearch.data.api.response.SearchRepositoryResponse
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.test.extentions.decodeFromStubData
import kotlinx.serialization.json.Json

class StubGithubService constructor(
    private val context: Context,
) : GithubService {
    override suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<RepositorySummary> {
        val format = Json { ignoreUnknownKeys = true }
        return format.decodeFromStubData<SearchRepositoryResponse>(
            context,
            format,
            "search_repositories_success.json"
        ).toModel()
    }

    override suspend fun fetchRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail {
        throw ApiErrorResult.UnexpectedError("stub2")
    }

}