package com.osamaaftab.freenowmvvm.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.osamaaftab.freenowmvvm.MainApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val NetWorkModule = module {

    single { providesRetrofit(get(), get()) }
    single { providesOkHttpClient() }
    single { createMoshiConverterFactory() }
}


fun providesRetrofit(
    okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(MainApplication().getApiUrl())
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
}

fun createMoshiConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun providesOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}