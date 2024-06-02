package com.example.testproducts.di

import android.content.Context
import androidx.room.Room
import com.example.testproducts.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

  @Singleton
  @Provides
  fun provideYourDatabase(@ApplicationContext app: Context) =
      Room.databaseBuilder(app, AppDatabase::class.java, "product_db").build()

  @Singleton @Provides fun provideYourDao(db: AppDatabase) = db.productDao()
}
