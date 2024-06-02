package com.example.testproducts.domain.interactor

import com.example.testproducts.core.network.NetworkConnectivityService
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.core.network.onError
import com.example.testproducts.core.network.onException
import com.example.testproducts.core.network.onSuccess
import com.example.testproducts.domain.RemoteProductsRepository
import com.example.testproducts.presentation.products.data.ProductsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchInteractor @Inject
constructor(
    private val localProductsRepository: LocalProductsRepository,
    private val remoteProductsRepository: RemoteProductsRepository,
    private val mutableStateFlow: MutableStateFlow<ProductsScreenState>,
    private val networkConnectivityService: NetworkConnectivityService,
) {

    suspend fun search(query: String) {
        if (networkConnectivityService.isConnected) {
            val result = remoteProductsRepository.searchProducts(query)
            result
                .onSuccess { product ->
                    mutableStateFlow.update { state ->
                        state.copy(product = product, isSearching = false)
                    }
                }
                .onError { _, message ->
                    processError(message.orEmpty())
                }
                .onException { throwable ->
                    processError(throwable.message.orEmpty())
                }
        } else {
            val result = localProductsRepository.searchDatabase(query)
            mutableStateFlow.update { state ->
                state.copy(product = state.product.copy(products = result), isSearching = false)
            }
        }

    }

    private fun processError(message: String) {
        mutableStateFlow.update { state ->
            state.copy(
                product = state.product.copy(products = emptyList()),
                isSearching = false,
                errorMessage = message
            )
        }
    }
}