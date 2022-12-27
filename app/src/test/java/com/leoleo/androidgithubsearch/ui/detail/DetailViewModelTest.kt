package com.leoleo.androidgithubsearch.ui.detail

import com.leoleo.androidgithubsearch.MainDispatcherRule
import com.leoleo.androidgithubsearch.data.ErrorResult
import com.leoleo.androidgithubsearch.data.SafeResult
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import org.mockito.Spy

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Spy
    private lateinit var repository: GithubRepoRepository
    private lateinit var viewModel: DetailViewModel

    private val ownerName = "flutter"
    private val data = RepositoryDetail(
        name = "flutter",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        stargazersCount = "147731",
        forksCount = "24075",
        openIssuesCount = "11390",
        subscribersCount = "3561",
        language = "Dart",
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun stateIsInitial() = runTest {
        assertEquals(UiState.Initial, viewModel.uiState)
    }

    @Test
    fun stateIsSuccess() = runTest {
        val job1 =
            launch(mainDispatcherRule.testDispatcher) {
                val retValue: RepositoryDetail = data
                doReturn(retValue).whenever(repository)
                    .getRepositoryDetail(ownerName, data.name)
                viewModel.getRepositoryDetail(ownerName, data.name)
            }
        assertEquals(UiState.Data(data), viewModel.uiState)
        job1.cancel()
    }

    @Test
    fun stateIsError() = runTest {
        val job1 =
            launch(mainDispatcherRule.testDispatcher) {
                val retValue = ErrorResult.NetworkError("error!")
                doReturn(retValue).whenever(repository).getRepositoryDetail(ownerName, data.name)
                viewModel.getRepositoryDetail(ownerName, data.name)
            }
        assert(viewModel.uiState is UiState.Error)
        job1.cancel()
    }
}