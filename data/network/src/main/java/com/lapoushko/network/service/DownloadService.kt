package com.lapoushko.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @author Lapoushko
 */
interface DownloadService {
    @HEAD
    suspend fun getHeadFile(@Url url: String) : Response<Unit>

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): Response<ResponseBody>
}