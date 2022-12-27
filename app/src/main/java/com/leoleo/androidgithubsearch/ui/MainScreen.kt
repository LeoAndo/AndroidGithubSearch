package com.leoleo.androidgithubsearch.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.leoleo.androidgithubsearch.ui.compact.CompactMainScreen
import com.leoleo.androidgithubsearch.ui.expanded.ExpandedMainScreen
import com.leoleo.androidgithubsearch.ui.medium.MediumMainScreen
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@Composable
fun MainScreen(windowWidthSizeClass: WindowWidthSizeClass) {
    AndroidGithubSearchTheme {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> CompactMainScreen()
            WindowWidthSizeClass.Medium -> MediumMainScreen()
            WindowWidthSizeClass.Expanded -> ExpandedMainScreen()
        }
    }
}