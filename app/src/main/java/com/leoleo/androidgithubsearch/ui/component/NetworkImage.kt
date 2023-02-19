package com.leoleo.androidgithubsearch.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.ui.preview.PreviewPhoneDevice

@Composable
fun AppNetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String = stringResource(id = R.string.no_content_description),
    transformations: List<Transformation> = emptyList(),
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    placeholder(drawableResId = R.drawable.placeholder_image)
                    error(drawableResId = R.drawable.error_image)
                    fallback(drawableResId = R.drawable.fallback_image)
                    transformations(transformations)
                }).build()
        ),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@PreviewPhoneDevice
@Composable
fun Prev_AppNetworkImage_Default() {
    AppSurface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppNetworkImage(
                imageUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
                contentDescription = "flutter owner icon",
                modifier = Modifier.size(60.dp),
            )
        }
    }
}

@PreviewPhoneDevice
@Composable
fun Prev_AppNetworkImage_CircleCrop() {
    AppSurface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppNetworkImage(
                imageUrl = "https://avatars.githubusercontent.com/u/14101776?v=4",
                contentDescription = "flutter owner icon",
                modifier = Modifier
                    .size(60.dp),
                transformations = listOf(CircleCropTransformation())
            )
        }
    }
}