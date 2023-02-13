package com.leoleo.androidgithubsearch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices

@Composable
fun AppLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@PreviewDevices
@Composable
private fun Prev_AppLoading() {
    AppSurface {
        AppLoading(modifier = Modifier.fillMaxSize())
    }
}