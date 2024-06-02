package com.example.testproducts.data.repository

import com.example.testproducts.core.network.NetworkResult
import com.example.testproducts.data.api.ProductsAPI
import com.example.testproducts.data.modelresponse.toNetworkResultProducts
import com.example.testproducts.domain.RemoteProductsRepository
import com.example.testproducts.domain.model.Products
import javax.inject.Inject

class RemoteProductsRepoImpl @Inject constructor(private val api: ProductsAPI) :
    RemoteProductsRepository {

    override suspend fun getProducts(limit: Int, skip: Int): NetworkResult<Products> {
        val result = api.getProducts(limit, skip)
        return result.toNetworkResultProducts()
    }
    override suspend fun searchProducts(searchQuery: String): NetworkResult<Products> {
        val result = api.searchProducts(searchQuery)
        return result.toNetworkResultProducts()
    }

}