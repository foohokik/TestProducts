package com.example.testproducts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: Int,
    val category: String,
    val description: String,
    val price: Double,
    val thumbnail: String,
    val title: String
)