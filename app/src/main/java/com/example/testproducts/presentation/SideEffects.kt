package com.example.testproducts.presentation

import com.example.testproducts.domain.model.ProductUI


sealed class SideEffects  {
    data class ErrorEffect(val err: String): SideEffects()
    data class ExceptionEffect(val throwable: Throwable): SideEffects()
    data class ClickEffect(val product: ProductUI.Product): SideEffects()
}
