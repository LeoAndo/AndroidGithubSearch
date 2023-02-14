package com.leoleo.androidgithubsearch.domain.usecase

import com.leoleo.androidgithubsearch.domain.di.DefaultDispatcher
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepositoryDetailUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: GithubRepoRepository
) {
    suspend operator fun invoke(ownerName: String, repositoryName: String): RepositoryDetail {
        return withContext(dispatcher) {
            repository.getRepositoryDetail(ownerName, repositoryName)
        }
    }
}