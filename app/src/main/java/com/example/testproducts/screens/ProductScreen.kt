package com.example.testproducts.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.testproducts.presentation.products.ProductsView

const val PRODUCT_SCREEN_ROUTE = "product"

fun NavGraphBuilder.productScreen(onProductClick: (Int) -> Unit) {
    composable(PRODUCT_SCREEN_ROUTE ) {
        ProductsView(onProductClick)
    }
}