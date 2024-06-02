package com.example.testproducts.di

import com.example.testproducts.data.repository.LocalProductsRepositoryImpl
import com.example.testproducts.data.repository.RemoteProductsRepoImpl
import com.example.testproducts.domain.LocalProductsRepository
import com.example.testproducts.domain.RemoteProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepoModule {

  @Binds fun bindRemoteRepository(repository: RemoteProductsRepoImpl): RemoteProductsRepository

  @Binds fun bindLocalRepository(repository: LocalProductsRepositoryImpl): LocalProductsRepository

}
