package com.leoleo.androidgithubsearch.ui.compact

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.MyNavHost
import com.leoleo.androidgithubsearch.ui.TopDestinations

@Composable
fun CompactMainScreen() {
    MyNavHost(
        startDestination = TopDestinations.SearchRoute.routeName,
        modifier = Modifier.testTag(stringResource(id = R.string.test_tag_compact_main_screen))
    )
}