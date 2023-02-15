package com.leoleo.androidgithubsearch.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.transform.CircleCropTransformation
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.domain.exception.ApiErrorType
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.ui.component.*
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    ownerName: String,
    name: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getRepositoryDetail(ownerName, name)
    })
    DetailScreenStateless(
        modifier = modifier.fillMaxSize(),
        uiState = viewModel.uiState,
        onReload = { viewModel.getRepositoryDetail(ownerName, name) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreenStateless(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onReload: () -> Unit,
) {
    Scaffold(topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) }) {
        val padding = PaddingValues(
            top = it.calculateTopPadding() + 12.dp,
            bottom = it.calculateBottomPadding() + 12.dp,
            start = it.calculateStartPadding(LayoutDirection.Ltr) + 12.dp,
            end = it.calculateEndPadding(LayoutDirection.Ltr) + 12.dp,
        )
        when (uiState) {
            UiState.Initial -> {}
            UiState.Loading -> AppLoading(modifier = Modifier.fillMaxSize())
            is UiState.Error -> {
                val throwable = uiState.throwable
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
                AppAlertDialog(
                    titleText = stringResource(id = R.string.app_name),
                    messageText = message,
                    confirmText = stringResource(id = android.R.string.ok),
                )
            }
            is UiState.Data -> {
                Column(
                    modifier = modifier
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val data = uiState.repositoryDetail
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AppNetworkImage(
                            imageUrl = data.ownerAvatarUrl,
                            contentDescription = stringResource(R.string.content_description_owner_avatar_icon),
                            modifier = Modifier.size(60.dp),
                            transformations = listOf(CircleCropTransformation())
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = data.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text(
                        text = stringResource(
                            id = R.string.stargazers_count,
                            data.stargazersCount
                        )
                    )
                    Text(text = stringResource(id = R.string.forks_count, data.forksCount))
                    Text(
                        text = stringResource(
                            id = R.string.open_issues_count,
                            data.openIssuesCount
                        )
                    )
                    Text(
                        text = stringResource(
                            id = R.string.watchers_count,
                            data.watchersCount
                        )
                    )
                    data.language?.let { Text(text = stringResource(id = R.string.language, it)) }
                }
            }
        }
    }
}

@PreviewDevices
@Composable
private fun Prev_DetailScreen() {
    val data = RepositoryDetail(
        name = "flutter",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
        stargazersCount = "147731",
        forksCount = "24075",
        openIssuesCount = "11390",
        watchersCount = "3561",
        language = "Dart",
    )
    AppSurface {
        DetailScreenStateless(
            modifier = Modifier
                .fillMaxSize(),
            uiState = UiState.Data(data),
            onReload = {},
        )
    }
}