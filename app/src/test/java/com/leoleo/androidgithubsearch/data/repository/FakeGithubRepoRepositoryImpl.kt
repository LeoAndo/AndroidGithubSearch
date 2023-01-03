package com.leoleo.androidgithubsearch.data.repository

import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.data.api.ApiErrorResult
import com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.data.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.data.domain.repository.GithubRepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubRepoRepositoryImpl :
    com.leoleo.androidgithubsearch.data.domain.repository.GithubRepoRepository {
    val ownerName = "flutter"
    val successData = com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail(
        name = "flutter",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        stargazersCount = "147731",
        forksCount = "24075",
        openIssuesCount = "11390",
        watchersCount = "3561",
        language = "Dart",
    )
    var isSuccess = true
    val errorData = com.leoleo.androidgithubsearch.data.api.ApiErrorResult.NetworkError
    override fun searchRepositories(query: String): Flow<PagingData<com.leoleo.androidgithubsearch.data.domain.model.RepositorySummary>> {
        return flowOf<PagingData<com.leoleo.androidgithubsearch.data.domain.model.RepositorySummary>>(PagingData.empty())
    }

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail {
        return if (isSuccess) successData else throw errorData
    }
}