package com.example.testproducts.domain

import com.example.testproducts.domain.model.ProductUI

interface LocalProductsRepository {

    suspend fun getProducts(): List<ProductUI.Product>

    suspend fun saveData(items: List<ProductUI.Product>)

    suspend fun searchDatabase(query: String): List<ProductUI.Product>

    suspend fun getProductById(id: Int): ProductUI.Product
}