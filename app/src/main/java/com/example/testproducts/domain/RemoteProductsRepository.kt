package com.example.testproducts.domain

import com.example.testproducts.core.network.NetworkResult
import com.example.testproducts.domain.model.Products

interface RemoteProductsRepository {

    suspend fun getProducts (limit:Int, skip:Int): NetworkResult<Products>
    suspend fun searchProducts (searchQuery: String): NetworkResult<Products>
}