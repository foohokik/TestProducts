package com.example.testproducts.di

import com.example.testproducts.presentation.SideEffects
import com.example.testproducts.presentation.products.data.ProductsScreenState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@InstallIn(ActivityRetainedComponent::class)
object ProductsFlowModule {

    @ActivityRetainedScoped
    @Provides
    fun provideMutableEffectChannel(): Channel<SideEffects> {
        return Channel()
    }

    @ActivityRetainedScoped
    @Provides
    fun provideMutableScreenStateFlow(): MutableStateFlow<ProductsScreenState> {
        return MutableStateFlow(ProductsScreenState())
    }
}