package com.leoleo.androidgithubsearch.ui.compact

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.ui.MyNavHost
import com.leoleo.androidgithubsearch.ui.TopDestinations

@Composable
fun CompactMainScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        MyNavHost(
            startDestination = TopDestinations.SearchRoute.routeName,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
    }
}