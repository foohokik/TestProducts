package com.example.testproducts.presentation.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproducts.di.IoDispatcher
import com.example.testproducts.domain.interactor.ProductInteractor
import com.example.testproducts.domain.interactor.SearchInteractor
import com.example.testproducts.presentation.SideEffects
import com.example.testproducts.presentation.products.data.ProductsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject
constructor(
    private val productInteractor: ProductInteractor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mutableScreenStateFlow: MutableStateFlow<ProductsScreenState>,
    private val searchInteractor: SearchInteractor,
    private val channel: Channel<SideEffects>
) : ViewModel() {

    val screenStateFlow: StateFlow<ProductsScreenState> = mutableScreenStateFlow.asStateFlow()
    var searchText by mutableStateOf("")
        private set

    private var updateTextJob: Job? = null
    private var changeableSearchText: String = searchText

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { channel.send(SideEffects.ExceptionEffect(throwable)) }
    }

    init {
        getProducts()
    }

    fun getProducts(isRefresh: Boolean = false) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            productInteractor.getProducts(isRefresh)
        }
    }

    fun onRefresh() {
        mutableScreenStateFlow.update { state ->
            state.copy(product = state.product.copy(products = emptyList()))
        }
        getProducts(true)
    }

    fun onSearchTextChange(query: String) {
        if (query == searchText) return
        searchText = query
        updateTextJob?.cancel()

        mutableScreenStateFlow.update { state ->
            state.copy(isSearching = true)
        }
        updateTextJob = viewModelScope.launch(ioDispatcher + exceptionHandler) {
            delay(SEARCH_DELAY)
            startSearch(query)
        }
    }

    fun onSearch() {
        if (changeableSearchText == searchText) return
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            startSearch(searchText)
        }
    }

    fun onClearSearch() {
        searchText = ""
        mutableScreenStateFlow.update { state ->
            state.copy(
                isSearching = false,
                product = state.product.copy(products = emptyList()),
                errorMessage = "",
                isLoading = false
            )
        }
    }

    private suspend fun startSearch(query: String) {
        searchInteractor.search(query)
    }

    companion object {
        private const val SEARCH_DELAY = 300L
    }
}
