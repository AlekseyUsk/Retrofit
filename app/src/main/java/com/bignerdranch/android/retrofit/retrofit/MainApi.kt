package com.bignerdranch.android.retrofit.retrofit

import com.bignerdranch.android.retrofit.retrofit.authentication.AuthRequest
import com.bignerdranch.android.retrofit.retrofit.authentication.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface MainApi {
    //запрос одного продукта
    @GET("products/{id}")
    suspend fun getProductById(@Part("id") id: Int): Product

    // запрос аутефикации username и password
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User

    //получить все продукты
    @GET("products")
    suspend fun getAllProducts(): Products
}