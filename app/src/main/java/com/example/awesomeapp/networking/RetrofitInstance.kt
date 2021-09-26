package com.example.awesomeapp.networking

import com.example.awesomeapp.model.PhotosResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInstance {

    @GET("/v1/curated")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Header("Authorization") api: String
    ): Response<PhotosResponse>

    companion object {
        fun create(): RetrofitInstance {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(RetrofitInstance::class.java)
        }
    }
}