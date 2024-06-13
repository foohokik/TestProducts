package com.example.testproducts.presentation.products


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.testproducts.R
import com.example.testproducts.domain.model.ProductUI
import com.example.testproducts.presentation.products.data.ListState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsView(
    onProductClick: (Int) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>()
) {
    val lazyColumnListState = rememberLazyListState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.screenStateFlow.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
    val refreshState = rememberPullRefreshState(state.isLoading, { viewModel.onRefresh() })

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()
            state.listState == ListState.IDLE && lastVisibleItem?.index != 0 && lastVisibleItem?.index == lazyColumnListState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            viewModel.getProducts()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchInputField(
            searchText = viewModel.searchText,
            isSearching = state.isSearching,
            onQueryChange =  viewModel::onSearchTextChange ,
            onStartSearch =  viewModel::onSearch,
            onClearSearch =  viewModel::onClearSearch
        )

        if (state.errorMessage.isNotEmpty()) {
            ErrorText(errorMessage = state.errorMessage)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState)
            ) {
                ProductsItems(lazyColumnListState, state.product.products, onProductClick)

                PullRefreshIndicator(
                    state.isLoading,
                    refreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun SearchInputField(
    searchText: String,
    isSearching: Boolean,
    onQueryChange: (String) -> Unit,
    onStartSearch: () -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onQueryChange,
        modifier = modifier.then(Modifier.fillMaxWidth()),
        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.placeholder),
                color = Color.Black
            )
        },
        shape = RoundedCornerShape(16.dp),
        keyboardActions = KeyboardActions(onSearch = { onStartSearch.invoke() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        leadingIcon = { Icon(Icons.Sharp.Search, contentDescription = "Search") },
        trailingIcon = {
            if (isSearching) {
                CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(16.dp))
            } else {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onClearSearch.invoke() }
                )
            }
        }
    )
}

@Composable
fun ProductsItems(
    lazyColumnListState: LazyListState,
    products: List<ProductUI>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(state = lazyColumnListState, modifier = modifier) {
        items(products, key = { it.title }) { content ->
            when (content) {
                is ProductUI.Product -> {
                    ProductItem(content, onProductClick)
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading", modifier = Modifier.padding(10.dp))
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                   }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(
    model: ProductUI.Product,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .clickable { onProductClick.invoke(model.id) }
        )
    ) {
        GlideImage(
            model = model.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 12.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(4f)
        ) {
            Text(text = model.title, color = Color.Black, fontSize = 16.sp)
            Text(
                text = model.description,
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = "${model.price}$",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
    }
}

@Composable
fun ErrorText(modifier: Modifier = Modifier, errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = errorMessage,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.then(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight()
            )
        )
    }
}

