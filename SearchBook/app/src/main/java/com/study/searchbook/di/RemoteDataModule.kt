package com.study.searchbook.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.study.searchbook.api.BookApi
import com.study.searchbook.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    // Gson DI
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    // Retrofit DI
    @Provides
    @Singleton
    @Named("mainRetrofit")
    fun provideRetrofitInstance(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApi(@Named("mainRetrofit") retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

}