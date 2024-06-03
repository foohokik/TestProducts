package com.example.testproducts.di

import com.example.testproducts.data.api.ProductsAPI
import com.example.testproducts.data.db.ProductDao
import com.example.testproducts.data.repository.LocalProductsRepositoryImpl
import com.example.testproducts.data.repository.RemoteProductsRepoImpl
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.domain.RemoteProductsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepoModule {

  @Binds
  fun bindRemoteRepository(repository: RemoteProductsRepoImpl): RemoteProductsRepository

  @Binds
  fun bindLocalRepository(repository: LocalProductsRepositoryImpl): LocalProductsRepository

}