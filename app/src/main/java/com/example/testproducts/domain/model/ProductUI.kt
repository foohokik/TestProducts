package com.example.testproducts.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ProductUI {
    abstract val title: String

    @Parcelize
    data class Product(
        val category: String = "",
        val description: String = "",
        val id: Int = 0,
        val price: Double = 0.0,
        val thumbnail: String = "",
        override val title: String = ""
    ) : ProductUI(), Parcelable

    data class Loading(override val title: String) : ProductUI()
}
