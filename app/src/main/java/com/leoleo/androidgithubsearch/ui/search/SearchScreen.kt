package com.leoleo.androidgithubsearch.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.data.ErrorResult
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.ui.components.ErrorFullScreen
import com.leoleo.androidgithubsearch.ui.components.LoadingFullScreen
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    modifier: Modifier,
    navigateToDetailScreen: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }
    var isSearched by rememberSaveable { mutableStateOf(false) } // TODO: このフラグ消したい.
    val githubRepositories = viewModel.githubRepositories.collectAsLazyPagingItems()
    SearchScreenStateless(
        modifier = modifier,
        githubRepositories = githubRepositories,
        loadState = githubRepositories.loadState,
        isSearched = isSearched,
        query = query,
        onValueChange = { query = it },
        onSearch = {
            isSearched = true
            viewModel.searchRepositories(query)
        },
        onClickCardItem = { ownerName, name -> navigateToDetailScreen(ownerName, name) },
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenStateless(
    modifier: Modifier,
    githubRepositories: LazyPagingItems<RepositorySummary>,
    loadState: CombinedLoadStates,
    isSearched: Boolean,
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
        Spacer(modifier = Modifier.size(20.dp))
        Log.d("SearchScreen", "loadState: $loadState")
        LazyColumn {
            when (val state = loadState.refresh) {
                is LoadState.NotLoading -> {
                    if (githubRepositories.itemCount == 0) {
                        item {
                            ErrorFullScreen(
                                message = stringResource(id = R.string.empty_message),
                                onReload = { onSearch() },
                            )
                        }
                    } else {
                        items(items = githubRepositories, key = { it.id }) {
                            it?.let { item ->
                                Card(
                                    onClick = { onClickCardItem(item.ownerName, item.name) },
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
                                            item.ownerName,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(item.name)
                                    }
                                }
                                Spacer(modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
                is LoadState.Loading -> {
                    if (isSearched) item { LoadingFullScreen() }
                }
                is LoadState.Error -> errorContent(throwable = state.error, onReload = onSearch)
            }

            when (val state = loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> item { LoadingFullScreen() }
                is LoadState.Error -> errorContent(throwable = state.error, onReload = onSearch)
            }
        }
    }
}

private fun LazyListScope.errorContent(
    throwable: Throwable,
    onReload: () -> Unit,
) {
    item {
        val message = if (throwable is ErrorResult) {
            when (throwable) {
                is ErrorResult.UnAuthorizedError -> stringResource(id = R.string.unauthorized_message)
                is ErrorResult.BadRequestError, is ErrorResult.NetworkError, is ErrorResult.NotFoundError,
                is ErrorResult.UnexpectedError -> {
                    throwable.message ?: stringResource(id = R.string.default_error_message)
                }
            }
        } else {
            throwable.localizedMessage ?: stringResource(id = R.string.default_error_message)
        }
        ErrorFullScreen(message = message, onReload = onReload)
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Initial_SearchScreen() {
    val githubRepositories =
        flowOf<PagingData<RepositorySummary>>(PagingData.empty()).collectAsLazyPagingItems()
    // 初期起動時のState
    val refreshState = LoadState.Loading // default: endOfPaginationReached=false
    val prependState = LoadState.NotLoading(endOfPaginationReached = false)
    val appendState = LoadState.NotLoading(endOfPaginationReached = false)
    val loadState = CombinedLoadStates(
        refresh = refreshState,
        prepend = prependState,
        append = appendState,
        source = LoadStates(
            refresh = refreshState,
            prepend = prependState,
            append = appendState
        ),
        mediator = null
    )
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {},
            onSearch = {},
            onClickCardItem = { _, _ -> },
            githubRepositories = githubRepositories,
            loadState = loadState,
            isSearched = false,
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Loading_SearchScreen() {
    val githubRepositories =
        flowOf<PagingData<RepositorySummary>>(PagingData.empty()).collectAsLazyPagingItems()
    // Loading時のState
    val refreshState = LoadState.Loading // default: endOfPaginationReached=false
    val prependState = LoadState.NotLoading(endOfPaginationReached = false)
    val appendState = LoadState.NotLoading(endOfPaginationReached = false)
    val loadState = CombinedLoadStates(
        refresh = refreshState,
        prepend = prependState,
        append = appendState,
        source = LoadStates(
            refresh = refreshState,
            prepend = prependState,
            append = appendState
        ),
        mediator = null
    )
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {},
            onSearch = {},
            onClickCardItem = { _, _ -> },
            githubRepositories = githubRepositories,
            loadState = loadState,
            isSearched = true,
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Success_Item_Empty_SearchScreen() {
    val githubRepositories =
        flowOf<PagingData<RepositorySummary>>(PagingData.empty()).collectAsLazyPagingItems()
    val refreshState = LoadState.NotLoading(endOfPaginationReached = false)
    val prependState = LoadState.NotLoading(endOfPaginationReached = true)
    val appendState = LoadState.NotLoading(endOfPaginationReached = false)
    val loadState = CombinedLoadStates(
        refresh = refreshState,
        prepend = prependState,
        append = appendState,
        source = LoadStates(
            refresh = refreshState,
            prepend = prependState,
            append = appendState
        ),
        mediator = null
    )
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {},
            onSearch = {},
            onClickCardItem = { _, _ -> },
            githubRepositories = githubRepositories,
            loadState = loadState,
            isSearched = true,
        )
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Error_SearchScreen() {
    val githubRepositories =
        flowOf<PagingData<RepositorySummary>>(PagingData.empty()).collectAsLazyPagingItems()
    // Air Plane Mode: Onの時のState
    val refreshState = LoadState.Error(
        error = ErrorResult.NetworkError(message = "Unable to resolve host \"api.github.com\": No address associated with hostname")
    ) // default: endOfPaginationReached=false
    val prependState = LoadState.NotLoading(endOfPaginationReached = false)
    val appendState = LoadState.NotLoading(endOfPaginationReached = false)
    val loadState = CombinedLoadStates(
        refresh = refreshState,
        prepend = prependState,
        append = appendState,
        source = LoadStates(
            refresh = refreshState,
            prepend = prependState,
            append = appendState
        ),
        mediator = null
    )
    AndroidGithubSearchTheme {
        SearchScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "android-compose",
            onValueChange = {},
            onSearch = {},
            onClickCardItem = { _, _ -> },
            githubRepositories = githubRepositories,
            loadState = loadState,
            isSearched = true,
        )
    }
}