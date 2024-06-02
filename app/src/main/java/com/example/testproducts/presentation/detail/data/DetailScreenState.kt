package com.example.testproducts.presentation.detail.data

import com.example.testproducts.domain.model.ProductUI

data class DetailScreenState(
    val isLoading: Boolean = true,
    val product: ProductUI.Product = ProductUI.Product()
)
