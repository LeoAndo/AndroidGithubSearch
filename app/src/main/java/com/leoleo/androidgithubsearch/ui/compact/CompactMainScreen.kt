package com.leoleo.androidgithubsearch.ui.compact

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.MyNavHost
import com.leoleo.androidgithubsearch.ui.TopDestinations
import com.leoleo.androidgithubsearch.ui.components.AppSurface

@Composable
fun CompactMainScreen() {
    AppSurface(
        modifier = Modifier.testTag(stringResource(id = R.string.test_tag_compact_main_screen)),
    ) {
        MyNavHost(
            startDestination = TopDestinations.SearchRoute.routeName,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
    }
}