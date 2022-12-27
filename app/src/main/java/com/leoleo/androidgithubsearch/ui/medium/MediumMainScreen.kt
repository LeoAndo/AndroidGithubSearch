package com.leoleo.androidgithubsearch.ui.medium

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme
import com.leoleo.androidgithubsearch.ui.MyNavHost
import com.leoleo.androidgithubsearch.ui.Page
import com.leoleo.androidgithubsearch.ui.TopDestinations
import com.leoleo.androidgithubsearch.ui.user.UserScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.leoleo.androidgithubsearch.ui.preview.PreviewTabletDevice

@Composable
fun MediumMainScreen() {
    val items = Page.values()
    var selectedItem by rememberSaveable { mutableStateOf(items[0]) }
    MediumMainScreenStateless(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        selectedItem = selectedItem,
        onClickNavigationRailItem = { item ->
            selectedItem = item
        })
}

@Composable
private fun MediumMainScreenStateless(
    modifier: Modifier,
    selectedItem: Page,
    onClickNavigationRailItem: (Page) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        Row {
            NavigationRail {
                Page.values().forEachIndexed { _, item ->
                    NavigationRailItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == item,
                        onClick = { onClickNavigationRailItem(item) }
                    )
                }
            }
            when (Page.values().firstOrNull { it == selectedItem }) {
                Page.HOME -> HomeScreen(modifier)
                Page.SEARCH -> {
                    MyNavHost(
                        startDestination = TopDestinations.SearchRoute.routeName,
                        modifier = modifier
                    )
                }
                Page.User -> UserScreen(modifier)
                null -> Text(
                    text = "$selectedItem is unknown.",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(text = stringResource(id = R.string.medium_screen_message))
        Text(text = stringResource(id = R.string.medium_screen_message2))
        Text(text = stringResource(id = R.string.medium_screen_message3))
    }
}

@PreviewTabletDevice
@Composable
private fun Prev_Home_MediumMainScreen() {
    AndroidGithubSearchTheme {
        MediumMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            selectedItem = Page.HOME,
            onClickNavigationRailItem = {},
        )
    }
}

@PreviewTabletDevice
@Composable
private fun Prev_Search_MediumMainScreen() {
    AndroidGithubSearchTheme {
        MediumMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            selectedItem = Page.SEARCH,
            onClickNavigationRailItem = {},
        )
    }
}

@PreviewTabletDevice
@Composable
private fun Prev_User_MediumMainScreen() {
    AndroidGithubSearchTheme {
        MediumMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            selectedItem = Page.User,
            onClickNavigationRailItem = {},
        )
    }
}