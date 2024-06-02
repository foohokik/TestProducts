package com.example.testproducts.data.db

import com.example.testproducts.data.entities.ProductEntity
import com.example.testproducts.domain.model.ProductUI

fun ProductEntity.toProduct(): ProductUI.Product {
    return ProductUI.Product(category, description, id, price, thumbnail, title)
}

fun List<ProductEntity>.toProducts(): List<ProductUI.Product> {
    return this.map { it.toProduct() }
}

fun ProductUI.Product.toProductEntity(): ProductEntity {
    return ProductEntity(id, category, description, price, thumbnail, title)
}

fun List<ProductUI.Product>.toProductEntities(): List<ProductEntity> {
    return this.map { it.toProductEntity() }
}
