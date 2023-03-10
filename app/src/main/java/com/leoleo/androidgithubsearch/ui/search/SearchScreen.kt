package com.leoleo.androidgithubsearch.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
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
import com.leoleo.androidgithubsearch.domain.exception.ApiErrorType
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.ui.component.AppError
import com.leoleo.androidgithubsearch.ui.component.AppLoading
import com.leoleo.androidgithubsearch.ui.component.AppSurface
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }
    var isSearched by rememberSaveable { mutableStateOf(false) } // TODO: ???????????????????????????.
    val githubRepositories = viewModel.githubRepositories.collectAsLazyPagingItems()
    SearchScreenStateless(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
            .testTag(stringResource(id = R.string.test_tag_search_screen)),
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
    modifier: Modifier = Modifier,
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
            isError = query.isEmpty()
        )
        Spacer(modifier = Modifier.size(12.dp))
        Log.d("SearchScreen", "loadState: $loadState")
        LazyColumn {
            when (val state = loadState.refresh) {
                is LoadState.NotLoading -> {
                    if (githubRepositories.itemCount == 0) {
                        item {
                            AppError(
                                message = stringResource(id = R.string.empty_message),
                                onReload = { onSearch() },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    } else {
                        items(items = githubRepositories) {
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
                    if (isSearched) item { AppLoading(modifier = Modifier.fillMaxSize()) }
                }
                is LoadState.Error -> errorContent(throwable = state.error, onReload = onSearch)
            }

            when (val state = loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> item { AppLoading(modifier = Modifier.fillMaxSize()) }
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
        val defaultErrorMessage = throwable.localizedMessage
            ?: stringResource(id = R.string.default_error_message)
        val message = if (throwable is ApiErrorType) {
            when (throwable) {
                ApiErrorType.Network -> stringResource(id = R.string.network_error_message)
                is ApiErrorType.NotFound, is ApiErrorType.Forbidden, is ApiErrorType.UnAuthorized,
                is ApiErrorType.UnprocessableEntity, is ApiErrorType.Unknown, ApiErrorType.Redirect, ApiErrorType.Server -> {
                    defaultErrorMessage
                }
            }
        } else {
            defaultErrorMessage
        }
        AppError(message = message, onReload = onReload, modifier = Modifier.fillMaxSize())
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_Initial_SearchScreen() {
    val githubRepositories =
        flowOf<PagingData<RepositorySummary>>(PagingData.empty()).collectAsLazyPagingItems()
    // ??????????????????State
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
    AppSurface {
        SearchScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            query = "",
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
    // Loading??????State
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
    AppSurface {
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
    AppSurface {
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
    // Air Plane Mode: On?????????State
    val refreshState = LoadState.Error(
        error = ApiErrorType.Network
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
    AppSurface {
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