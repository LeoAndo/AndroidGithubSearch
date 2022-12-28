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
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.data.ErrorResult
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
            val message =
                throwable.localizedMessage ?: stringResource(id = R.string.default_error_message)
            if (throwable is ErrorResult) {
                when (throwable) {
                    is ErrorResult.NotFoundError -> {
                        ErrorFullScreen(message = message, onReload = { onReload() })
                        AppAlertDialog(
                            titleText = message,
                            messageText = stringResource(id = R.string.page_not_found_message),
                            confirmText = stringResource(id = android.R.string.ok),
                        )
                    }
                    is ErrorResult.ForbiddenError -> {
                        ErrorFullScreen(
                            message = stringResource(id = R.string.forbidden_error_message),
                            onReload = { onReload() })
                    }
                    is ErrorResult.BadRequestError, is ErrorResult.NetworkError, is ErrorResult.UnAuthorizedError,
                    is ErrorResult.UnexpectedError -> {
                        ErrorFullScreen(message = message, onReload = { onReload() })
                    }
                }
            } else {
                ErrorFullScreen(message = message, onReload = { onReload() })
            }
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
            uiState = UiState.Data(data),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            onReload = {},
        )
    }
}