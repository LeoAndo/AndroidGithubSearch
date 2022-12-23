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
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.ui.components.ErrorFullScreen
import com.leoleo.androidgithubsearch.ui.components.LoadingFullScreen
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice

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
            is SearchUiState.Error -> ErrorFullScreen(
                message = uiState.message,
                onReload = { onSearch() },
            )
            SearchUiState.Loading -> LoadingFullScreen()
            SearchUiState.Empty -> ErrorFullScreen(
                message = stringResource(id = R.string.empty_message),
                onReload = { onSearch() },
            )
            SearchUiState.Initial -> {}

        }
    }
}

@PreviewDevices
@Composable
private fun Prev_Success_SearchScreen() {
    val data = buildList {
        (0..20).forEach {
            add(
                RepositorySummary(
                    "android-compose $it",
                    "google"
                )
            )
        }
    }
    SearchScreenStateless(
        uiState = SearchUiState.Data(data),
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        query = "android-compose",
        onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
    )
}

@PreviewPhoneDevice
@Composable
private fun Prev_Error_SearchScreen() {
    SearchScreenStateless(
        uiState = SearchUiState.Error(message = "error!!"),
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        query = "android-compose",
        onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
    )
}

@PreviewPhoneDevice
@Composable
private fun Prev_Loading_SearchScreen() {
    SearchScreenStateless(
        uiState = SearchUiState.Loading,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        query = "android-compose",
        onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
    )
}

@PreviewPhoneDevice
@Composable
private fun Prev_Initial_SearchScreen() {
    SearchScreenStateless(
        uiState = SearchUiState.Initial,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        query = "android-compose",
        onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
    )
}

@PreviewPhoneDevice
@Composable
private fun Prev_Empty_SearchScreen() {
    SearchScreenStateless(
        uiState = SearchUiState.Empty,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        query = "android-compose",
        onValueChange = {}, onSearch = {}, onClickCardItem = { _, _ -> },
    )
}