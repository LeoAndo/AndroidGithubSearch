package com.leoleo.androidgithubsearch.ui.expanded

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.MyNavHost
import com.leoleo.androidgithubsearch.ui.Page
import com.leoleo.androidgithubsearch.ui.TopDestinations
import com.leoleo.androidgithubsearch.ui.preview.PreviewFoldableDevice
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme
import com.leoleo.androidgithubsearch.ui.user.UserScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedMainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = Page.values()
    var selectedItem by rememberSaveable { mutableStateOf(items[0]) }
    val modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .testTag(stringResource(id = R.string.test_tag_expanded_main_screen))
    ExpandedMainScreenStateless(
        modifier = modifier,
        drawerState = drawerState,
        scope = scope,
        selectedItem = selectedItem,
        items = items,
        onClickDrawerItem = { item -> selectedItem = item }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedMainScreenStateless(
    modifier: Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Page,
    items: Array<Page>,
    onClickDrawerItem: (Page) -> Unit,
) {
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        DismissibleNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DismissibleDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = item == selectedItem,
                            onClick = {
                                scope.launch { drawerState.close() }
                                onClickDrawerItem(item)
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            },
            content = {
                when (Page.values().firstOrNull { it.ordinal == selectedItem.ordinal }) {
                    Page.HOME -> {
                        HomeScreen(modifier, drawerState, onClickDrawerControlBtn = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        })
                    }
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
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier,
    drawerState: DrawerState,
    onClickDrawerControlBtn: () -> Unit,
) {
    val buttonText = if (drawerState.isClosed) {
        "Click to open"
    } else {
        "Click to close"
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onClickDrawerControlBtn() }) {
            Text(buttonText)
        }
        Text(text = stringResource(id = R.string.medium_screen_message))
        Text(text = stringResource(id = R.string.medium_screen_message2))
        Text(text = stringResource(id = R.string.medium_screen_message3))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewFoldableDevice
@Composable
private fun Prev_Open_Home_ExpandedMainScreen() {
    AndroidGithubSearchTheme {
        ExpandedMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            drawerState = rememberDrawerState(DrawerValue.Open),
            scope = rememberCoroutineScope(),
            selectedItem = Page.HOME,
            items = Page.values(),
            onClickDrawerItem = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewFoldableDevice
@Composable
private fun Prev_Open_Search_ExpandedMainScreen() {
    AndroidGithubSearchTheme {
        ExpandedMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            drawerState = rememberDrawerState(DrawerValue.Open),
            scope = rememberCoroutineScope(),
            selectedItem = Page.SEARCH,
            items = Page.values(),
            onClickDrawerItem = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewFoldableDevice
@Composable
private fun Prev_Open_User_ExpandedMainScreen() {
    AndroidGithubSearchTheme {
        ExpandedMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            drawerState = rememberDrawerState(DrawerValue.Open),
            scope = rememberCoroutineScope(),
            selectedItem = Page.User,
            items = Page.values(),
            onClickDrawerItem = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewFoldableDevice
@Composable
private fun Prev_Close_User_ExpandedMainScreen() {
    AndroidGithubSearchTheme {
        ExpandedMainScreenStateless(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            drawerState = rememberDrawerState(DrawerValue.Closed),
            scope = rememberCoroutineScope(),
            selectedItem = Page.User,
            items = Page.values(),
            onClickDrawerItem = { }
        )
    }
}

