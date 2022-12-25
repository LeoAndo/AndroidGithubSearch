package com.leoleo.androidgithubsearch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String = stringResource(id = R.string.no_content_description),
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
        modifier = modifier,
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(500)
                    placeholder(drawableResId = R.drawable.placeholder_image)
                }
            ),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@PreviewDevices
@Composable
fun Prev_AvatarImage() {
    AndroidGithubSearchTheme {
        AvatarImage(
            imageUrl = "photo.urls.regular",
            contentDescription = "photo.user.username",
            modifier = Modifier.size(120.dp)
        )
    }
}