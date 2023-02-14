package com.leoleo.androidgithubsearch.domain.usecase

import com.leoleo.androidgithubsearch.domain.MainDispatcherRule
import com.leoleo.androidgithubsearch.domain.fake.FakeGithubRepoRepository
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetRepositoryDetailUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeGithubRepoRepository()
    private lateinit var getRepositoryDetailUseCase: GetRepositoryDetailUseCase

    @Before
    fun setUp() {
        getRepositoryDetailUseCase =
            GetRepositoryDetailUseCase(mainDispatcherRule.testDispatcher, repository)
    }

    @Test
    fun stateIsSuccess() = runTest {
        repository.isSuccess = true
        var ret: RepositoryDetail? = null
        val job1 = launch(mainDispatcherRule.testDispatcher) {
            ret = getRepositoryDetailUseCase(repository.ownerName, repository.successData.name)
        }
        assertEquals(repository.successData, ret)
        job1.cancel()
    }

    @Test
    fun stateIsError() = runTest {
        repository.isSuccess = false
        var ret: RepositoryDetail? = null
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
            assertEquals(repository.errorData, ret)
        }
        val job1 = launch(coroutineExceptionHandler) {
            ret = getRepositoryDetailUseCase(repository.ownerName, repository.successData.name)
        }
        job1.cancel()
    }
}