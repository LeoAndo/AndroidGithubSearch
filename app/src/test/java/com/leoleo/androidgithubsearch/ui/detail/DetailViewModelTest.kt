package com.leoleo.androidgithubsearch.ui.detail

import com.leoleo.androidgithubsearch.MainDispatcherRule
import com.leoleo.androidgithubsearch.data.repository.FakeGithubRepoRepositoryImpl
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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeGithubRepoRepositoryImpl()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun stateIsInitial() = runTest {
        assertEquals(UiState.Initial, viewModel.uiState)
    }

    @Test
    fun stateIsSuccess() = runTest {
        repository.isSuccess = true
        val job1 =
            launch(mainDispatcherRule.testDispatcher) {
                viewModel.getRepositoryDetail(repository.ownerName, repository.successData.name)
            }
        assertEquals(UiState.Data(repository.successData), viewModel.uiState)
        job1.cancel()
    }

    @Test
    fun stateIsError() = runTest {
        repository.isSuccess = false
        val job1 =
            launch(mainDispatcherRule.testDispatcher) {
                viewModel.getRepositoryDetail(repository.ownerName, repository.successData.name)
            }
        assertEquals(UiState.Error(repository.errorData), viewModel.uiState)
        job1.cancel()
    }
}