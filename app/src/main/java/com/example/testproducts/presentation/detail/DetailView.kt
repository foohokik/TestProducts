package com.example.testproducts.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.testproducts.R


@Composable
fun DetailView(
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.screenState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp), color = Color.Black)
            }
        } else {
            DetailViewImage(
                state.product.thumbnail,
                Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            DetailViewTitle(state.product.title)
            DetailViewProperty(
                stringResource(id = R.string.category_property),
                state.product.category
            )
            DetailViewProperty(
                stringResource(id = R.string.price_property),
                "${state.product.price}$"
            )
            DetailViewProperty(
                stringResource(id = R.string.description_property),
                state.product.description
            )
        }
    }
}

@Composable
fun DetailViewProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Visible
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailViewImage(thumbnail: String, modifier: Modifier = Modifier) {
    GlideImage(
        model = thumbnail,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun DetailViewTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
    )
}