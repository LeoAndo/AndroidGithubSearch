package com.leoleo.androidgithubsearch.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices

@Composable
fun ErrorFullScreen(
    modifier: Modifier = Modifier,
    message: String,
    onReload: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onReload) {
            Text(text = stringResource(R.string.reload))
        }
    }
}

@PreviewDevices
@Composable
private fun Prev_ErrorFullScreen() {
    ErrorFullScreen(
        message = "Could not load.",
        onReload = {},
    )
}