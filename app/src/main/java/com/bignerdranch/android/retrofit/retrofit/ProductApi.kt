package com.bignerdranch.android.retrofit.retrofit

import retrofit2.http.GET
import retrofit2.http.Part

interface ProductApi {
    @GET("products/{id}")
    suspend fun getProductById(@Part("id") id: Int): Product
}