package com.hk.transactionsapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hk.transactionsapp.domain.TransactionsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://60220907ae8f8700177dee68.mockapi.io/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionsService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

}