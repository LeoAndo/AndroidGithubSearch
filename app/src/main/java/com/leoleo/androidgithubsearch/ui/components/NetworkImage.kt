package com.leoleo.androidgithubsearch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.preview.PreviewDevices
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String = stringResource(id = R.string.no_content_description),
    transformations: List<Transformation> = emptyList(),
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                placeholder(drawableResId = R.drawable.placeholder_image)
                error(drawableResId = R.drawable.error_image)
                fallback(drawableResId = R.drawable.fallback_image)
                transformations(transformations)
            }
        ),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@PreviewDevices
@Composable
fun Prev_NetworkImage_Default() {
    AndroidGithubSearchTheme {
        NetworkImage(
            imageUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
            contentDescription = "flutter owner icon",
            modifier = Modifier.size(60.dp),
        )
    }
}

@PreviewDevices
@Composable
fun Prev_NetworkImage_CircleCrop() {
    AndroidGithubSearchTheme {
        NetworkImage(
            imageUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
            contentDescription = "flutter owner icon",
            modifier = Modifier.size(60.dp),
            transformations = listOf(CircleCropTransformation())
        )
    }
}