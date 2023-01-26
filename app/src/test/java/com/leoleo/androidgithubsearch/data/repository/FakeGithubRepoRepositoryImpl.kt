package com.leoleo.androidgithubsearch.data.repository

import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.domain.exception.ApiErrorType
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubRepoRepositoryImpl : GithubRepoRepository {
    val ownerName = "flutter"
    val successData = RepositoryDetail(
        name = "flutter",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        stargazersCount = "147731",
        forksCount = "24075",
        openIssuesCount = "11390",
        watchersCount = "3561",
        language = "Dart",
    )
    var isSuccess = true
    val errorData = ApiErrorType.Network
    override fun searchRepositories(query: String): Flow<PagingData<RepositorySummary>> {
        return flowOf<PagingData<RepositorySummary>>(PagingData.empty())
    }

    override suspend fun getRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail {
        return if (isSuccess) successData else throw errorData
    }
}