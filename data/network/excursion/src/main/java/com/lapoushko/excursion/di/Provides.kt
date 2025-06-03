package com.lapoushko.excursion.di

import com.lapoushko.excursion.service.DownloadService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author Lapoushko
 */
fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

fun provideApiService(retrofit: Retrofit): DownloadService = retrofit.create(DownloadService::class.java)

private const val BASE_URL = "https://storage.yandexcloud.net/"