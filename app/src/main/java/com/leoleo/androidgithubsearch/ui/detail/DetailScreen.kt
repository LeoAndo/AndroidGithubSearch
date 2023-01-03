package com.leoleo.androidgithubsearch.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.transform.CircleCropTransformation
import com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.data.api.ApiErrorResult
import com.leoleo.androidgithubsearch.ui.components.*

@Composable
fun DetailScreen(
    modifier: Modifier,
    ownerName: String,
    name: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getRepositoryDetail(ownerName, name)
    })
    DetailScreenStateless(
        uiState = viewModel.uiState,
        modifier = modifier,
        onReload = { viewModel.getRepositoryDetail(ownerName, name) },
    )
}

@Composable
private fun DetailScreenStateless(
    uiState: UiState,
    modifier: Modifier,
    onReload: () -> Unit,
) {
    when (uiState) {
        UiState.Initial -> {}
        UiState.Loading -> LoadingFullScreen()
        is UiState.Error -> {
            val throwable = uiState.throwable
            val defaultErrorMessage = throwable.localizedMessage
                ?: stringResource(id = R.string.default_error_message)
            val message = if (throwable is com.leoleo.androidgithubsearch.data.api.ApiErrorResult) {
                when (throwable) {
                    com.leoleo.androidgithubsearch.data.api.ApiErrorResult.NetworkError -> stringResource(id = R.string.network_error_message)
                    is com.leoleo.androidgithubsearch.data.api.ApiErrorResult.NotFoundError, is com.leoleo.androidgithubsearch.data.api.ApiErrorResult.ForbiddenError, is com.leoleo.androidgithubsearch.data.api.ApiErrorResult.UnAuthorizedError,
                    is com.leoleo.androidgithubsearch.data.api.ApiErrorResult.UnprocessableEntity, is com.leoleo.androidgithubsearch.data.api.ApiErrorResult.UnexpectedError -> {
                        defaultErrorMessage
                    }
                }
            } else {
                defaultErrorMessage
            }
            ErrorFullScreen(message = message, onReload = onReload)
            AppAlertDialog(
                titleText = stringResource(id = R.string.app_name),
                messageText = message,
                confirmText = stringResource(id = android.R.string.ok),
            )
        }
        is UiState.Data -> {
            Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
                val data = uiState.repositoryDetail
                Row(verticalAlignment = Alignment.CenterVertically) {
                    NetworkImage(
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

@PreviewDevices
@Composable
private fun Prev_DetailScreen() {
    val data = com.leoleo.androidgithubsearch.data.domain.model.RepositoryDetail(
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
            uiState = UiState.Data(data),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            onReload = {},
        )
    }
}