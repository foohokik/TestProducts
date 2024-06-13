package com.example.testproducts.data.repository

import android.util.Log
import com.example.testproducts.data.db.ProductDao
import com.example.testproducts.data.db.toProductEntities
import com.example.testproducts.data.db.toProducts
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.data.db.toProduct
import com.example.testproducts.domain.model.ProductUI
import javax.inject.Inject

class LocalProductsRepositoryImpl @Inject constructor(private val dao: ProductDao) :
    LocalProductsRepository {
    override suspend fun getProducts(): List<ProductUI.Product> {
        return dao.getAll().toProducts()
    }

    override suspend fun saveData(items: List<ProductUI.Product>) {
        Log.d("WRK", "items in LocalProductsRepositoryImpl = $items")
        dao.insertAll(items.toProductEntities())
    }

    override suspend fun searchDatabase(query: String): List<ProductUI.Product> {
        return dao.searchDatabase(query).toProducts()
    }

    override suspend fun getProductById(id: Int): ProductUI.Product {
        return dao.getProductById(id).toProduct()
    }

}
