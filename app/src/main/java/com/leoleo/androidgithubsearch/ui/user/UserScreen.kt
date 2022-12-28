package com.leoleo.androidgithubsearch.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.components.AppSurface
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice
import com.leoleo.androidgithubsearch.ui.util.AppLaunchHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(modifier: Modifier) {
    val items = listOf("Twitter", "Instagram", "Github")
    val twitterUrl = stringResource(R.string.my_twitter_url)
    val instagramUrl = stringResource(R.string.my_instagram_url)
    val githubUrl = stringResource(R.string.my_github_url)
    val appLaunchHelper = AppLaunchHelper(LocalContext.current)
    LazyColumn(modifier = modifier) {
        itemsIndexed(items) { _, data ->
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                onClick = {
                    when (data) {
                        "Twitter" -> appLaunchHelper.launchBrowserApp(twitterUrl)
                        "Instagram" -> appLaunchHelper.launchBrowserApp(instagramUrl)
                        "Github" -> appLaunchHelper.launchBrowserApp(githubUrl)
                    }
                },
            ) {
                Text(
                    text = data, modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@PreviewPhoneDevice
@Composable
private fun Prev_UserScreen() {
    AppSurface {
        UserScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
    }
}