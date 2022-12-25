package com.leoleo.androidgithubsearch.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.data.ErrorResult
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.ui.components.AppAlertDialog
import com.leoleo.androidgithubsearch.ui.components.ErrorFullScreen
import com.leoleo.androidgithubsearch.ui.components.LoadingFullScreen
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@Composable
fun SearchScreen(
    modifier: Modifier,
    navigateToDetailScreen: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }
    SearchScreenStateless(
        uiState = viewModel.uiState,
        modifier = modifier,
        query = query,
        onValueChange = { query = it },
        onSearch = { viewModel.searchRepositories(query, 1) },
        onClickCardItem = { ownerName, name -> navigateToDetailScreen(ownerName, name) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreenStateless(
    uiState: SearchUiState,
    modifier: Modifier,
    query: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClickCardItem: (String, String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = modifier) {
        OutlinedTextField(
            value = query,
            label = {
                Text(text = stringResource(R.string.label_search_keyword))
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onSearch()
            }),
            onValueChange = { onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
        )
        when (uiState) {
            SearchUiState.Initial -> {}
            SearchUiState.Loading -> LoadingFullScreen()
            SearchUiState.Empty -> ErrorFullScreen(
                message = stringResource(id = R.string.empty_message),
                onReload = { onSearch() },
            )
            is SearchUiState.Error -> {
                val throwable = uiState.throwable
                val message = throwable.localizedMessage
                    ?: stringResource(id = R.string.default_error_message)
                if (throwable is ErrorResult) {
                    when (throwable) {
                        is ErrorResult.UnAuthorizedError -> {
                            ErrorFullScreen(message = message, onReload = { onSearch() })
                            AppAlertDialog(
                                titleText = message,
                                messageText = stringResource(id = R.string.unauthorized_message),
                                confirmText = stringResource(id = android.R.string.ok),
                            )
                        }
                        is ErrorResult.BadRequestError, is ErrorResult.NetworkError, is ErrorResult.NotFoundError,
                        is ErrorResult.UnexpectedError -> {
                            ErrorFullScreen(message = message, onReload = { onSearch() })
                        }
                    }
                } else {
                    ErrorFullScreen(message = message, onReload = { onSearch() })
                }
            }
            is SearchUiState.Data -> {
                Spacer(modifier = Modifier.size(20.dp))
                LazyColumn(content = {
                    itemsIndexed(uiState.repoList) { _, repositoryData ->
                        Card(
                            onClick = {
                                onClickCardItem(
                                    repositoryData.ownerName,
                                    repositoryData.name
                                )
                            },
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    repositoryData.ownerName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(repositoryData.name)
                            }
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                })
            }
        }
    }
}

@PreviewDevices
@Composable
private fun Prev_Success_SearchScreen() {
    val data = buildList {
        repeat(20) {
            add(
                RepositorySummary(
                    ownerName = "google $it",
                    name = "android-compose"
                )
            )
        }
    }
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            uiState = SearchUiState.Data(data),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Error_SearchScreen() {
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            uiState = SearchUiState.Error(IllegalStateException("error!!")),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Loading_SearchScreen() {
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            uiState = SearchUiState.Loading,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Initial_SearchScreen() {
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            uiState = SearchUiState.Initial,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Empty_SearchScreen() {
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            uiState = SearchUiState.Empty,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
        )
    }
}