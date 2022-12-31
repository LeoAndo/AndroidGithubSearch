package com.leoleo.androidgithubsearch.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@Composable
fun AppSurface(
    content: @Composable () -> Unit
) {
    AndroidGithubSearchTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = MaterialTheme.colorScheme.background,
            content = content,
        )
    }
}

@PreviewDevices
@Composable
private fun Prev_AppSurface() {
    AppSurface {
        Text(
            text = "Hello, World!",
            modifier = Modifier.wrapContentSize()
        )
    }
}