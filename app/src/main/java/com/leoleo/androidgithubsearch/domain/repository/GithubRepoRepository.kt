package com.leoleo.androidgithubsearch.domain.repository

import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary

interface GithubRepoRepository {
    suspend fun searchRepositories(query: String, page: Int): List<RepositorySummary>
    suspend fun getRepositoryDetail(ownerName: String, repositoryName: String): RepositoryDetail
}