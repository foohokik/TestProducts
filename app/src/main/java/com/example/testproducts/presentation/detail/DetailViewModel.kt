package com.example.testproducts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproducts.di.IoDispatcher
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.presentation.detail.data.DetailScreenState
import com.example.testproducts.screens.PRODUCT_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject
constructor(
    private val localProductsRepository: LocalProductsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[PRODUCT_ID_ARG])

    private val _screenState = MutableStateFlow(DetailScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        getProductInfo()
    }

    private fun getProductInfo() {
        viewModelScope.launch(ioDispatcher) {
            val product = localProductsRepository.getProductById(id)
            _screenState.update { state ->
                state.copy(isLoading = false, product = product)
            }
        }
    }
}