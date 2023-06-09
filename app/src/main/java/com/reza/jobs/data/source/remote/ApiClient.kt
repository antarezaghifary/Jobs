package com.reza.jobs.data.source.remote

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.reza.jobs.BuildConfig
import com.reza.jobs.data.source.endpoint.ApiService
import com.reza.jobs.util.CustomDeserializer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideCacheInterceptor() = run {
    Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge =
            60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
}

fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

fun provideMoshiConverter(): Moshi = run {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideApi(
    baseUrl: String,
    moshiConverter: Moshi
): ApiService {

    val client: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(provideCacheInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
//        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .addConverterFactory(
            GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(List::class.java, CustomDeserializer())
                .create()
        ))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiService::class.java)
}