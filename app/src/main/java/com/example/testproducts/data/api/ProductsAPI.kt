package com.example.testproducts.data.api

import com.example.testproducts.core.network.NetworkResult
import com.example.testproducts.data.modelresponse.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsAPI {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): NetworkResult<ProductsResponse>

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") searchQuery: String
    ): NetworkResult<ProductsResponse>

}