package com.example.testproducts.di

import com.example.testproducts.core.network.NetworkConnectivityService
import com.example.testproducts.core.network.NetworkConnectivityServiceImpl
import com.example.testproducts.core.network.NetworkResultCallAdapterFactory
import com.example.testproducts.data.api.ProductsAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun networkConnectivityService(
        networkConnectivityService: NetworkConnectivityServiceImpl
    ): NetworkConnectivityService


    companion object {
        const val BASE_URL = "https://dummyjson.com"

        @Provides
        fun provideGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()
        }

        @Provides
        fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ProductsAPI =
            retrofit.create(ProductsAPI::class.java)
    }
}
