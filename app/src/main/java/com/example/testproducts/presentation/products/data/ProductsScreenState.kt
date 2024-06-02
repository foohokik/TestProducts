package com.example.testproducts.presentation.products.data

import com.example.testproducts.domain.model.Products
import javax.annotation.concurrent.Immutable

@Immutable
data class ProductsScreenState(
    val isLoading: Boolean = false,
    val product: Products = Products(),
    val listState: ListState = ListState.IDLE,
    val errorMessage: String = "",
    val isSearching: Boolean = false
)
