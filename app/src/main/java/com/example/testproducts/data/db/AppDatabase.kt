package com.example.testproducts.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testproducts.data.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}