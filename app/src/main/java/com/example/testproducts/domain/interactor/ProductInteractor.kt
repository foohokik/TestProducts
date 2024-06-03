package com.example.testproducts.domain.interactor

import com.example.testproducts.core.network.onError
import com.example.testproducts.core.network.onException
import com.example.testproducts.core.network.onSuccess
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.domain.RemoteProductsRepository
import com.example.testproducts.domain.model.ProductUI
import com.example.testproducts.presentation.products.data.ListState
import com.example.testproducts.presentation.products.data.ProductsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ProductInteractor
@Inject constructor(
    private val localProductsRepository: LocalProductsRepository,
    private val remoteProductsRepository: RemoteProductsRepository,
    private val mutableStateFlow: MutableStateFlow<ProductsScreenState>
) {
    private var skip = 0

    suspend fun getProducts(isRefresh: Boolean = false) {
        if (mutableStateFlow.value.product.products.isNotEmpty() && mutableStateFlow.value.product.products.size < LIMIT - 1) return
        skip = if (isRefresh) {
            0
        } else {
            skip
        }
        mutableStateFlow.update { state ->
            if (skip >= LIMIT) {
                val product = state.product.copy(
                    products = state.product.products.toMutableList() + listOf(ProductUI.Loading("TitleForLoading".random() + state.product.products.random().title))
                )
                state.copy(
                    listState = ListState.PAGINATING, product = product, errorMessage = ""
                )
            } else {
                state.copy(isLoading = true, errorMessage = "")
            }
        }
        val remoteProducts = remoteProductsRepository.getProducts(LIMIT, skip)


        remoteProducts.onSuccess { result ->
            localProductsRepository.saveData(result.products.filterIsInstance<ProductUI.Product>())
            mutableStateFlow.update { state ->
                val product =
                    state.product.copy(products = (state.product.products + result.products).filterIsInstance<ProductUI.Product>())
                state.copy(
                    product = product, isLoading = false
                )
            }
            if (skip < result.total) {
                skip += LIMIT
            }
        }.onError { _, message ->
            getLocalDataOrError(message.orEmpty())
        }.onException { throwable ->
            getLocalDataOrError(throwable.message.orEmpty())
        }
        mutableStateFlow.update { state ->
            state.copy(listState = ListState.IDLE)
        }
    }

    suspend fun getAllData() {
        val remoteProducts = remoteProductsRepository.getProducts(0, 0)
        remoteProducts.onSuccess { result ->
            localProductsRepository.saveData(result.products.filterIsInstance<ProductUI.Product>())
//            mutableStateFlow.update { state ->
//                val product =
//                    state.product.copy(
//                        products = (state.product.products + result.products)
//                            .filterIsInstance<ProductUI.Product>()
//                    )
//                state.copy(
//                    product = product, isLoading = false
//                )
//            }
        }.onError { _, message ->
            getLocalDataOrError(message.orEmpty())
        }.onException { throwable ->
            getLocalDataOrError(throwable.message.orEmpty())
        }
    }

    private suspend fun getLocalDataOrError(message: String) {
        val items = localProductsRepository.getProducts()
        mutableStateFlow.update { state ->
            if (items.isNotEmpty()) {
                state.copy(product = state.product.copy(products = items), isLoading = false)
            } else {
                state.copy(errorMessage = message, isLoading = false)
            }
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}
