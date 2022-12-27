package com.leoleo.androidgithubsearch.domain.repository

import androidx.paging.PagingData
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import kotlinx.coroutines.flow.Flow

interface GithubRepoRepository {
    fun searchRepositories(query: String): Flow<PagingData<RepositorySummary>>
    suspend fun getRepositoryDetail(ownerName: String, repositoryName: String): RepositoryDetail
}