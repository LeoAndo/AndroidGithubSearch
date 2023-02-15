package com.leoleo.androidgithubsearch.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.leoleo.androidgithubsearch.ui.compact.CompactMainScreen
import com.leoleo.androidgithubsearch.ui.component.AppSurface
import com.leoleo.androidgithubsearch.ui.expanded.ExpandedMainScreen
import com.leoleo.androidgithubsearch.ui.medium.MediumMainScreen

@Composable
fun MainScreen(windowWidthSizeClass: WindowWidthSizeClass) {
    AppSurface {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> CompactMainScreen()
            WindowWidthSizeClass.Medium -> MediumMainScreen()
            WindowWidthSizeClass.Expanded -> ExpandedMainScreen()
        }
    }
}