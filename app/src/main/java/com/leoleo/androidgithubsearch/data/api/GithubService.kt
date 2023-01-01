package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary

interface GithubService {
    companion object {
        const val SEARCH_PER_PAGE = 20
    }

    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int = SEARCH_PER_PAGE,
        sort: String = "stars"
    ): List<RepositorySummary>

    suspend fun fetchRepositoryDetail(ownerName: String, repositoryName: String): RepositoryDetail
}