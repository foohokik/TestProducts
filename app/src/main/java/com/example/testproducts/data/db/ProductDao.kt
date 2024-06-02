package com.example.testproducts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testproducts.data.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.title LIKE :searchQuery || '%'")
    fun searchDatabase(searchQuery: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.id = :id")
    suspend fun getProductById(id: Int): ProductEntity

}