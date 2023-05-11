package com.bignerdranch.android.retrofit.retrofit

import com.bignerdranch.android.retrofit.retrofit.authentication.AuthRequest
import com.bignerdranch.android.retrofit.retrofit.authentication.User
import retrofit2.http.*

interface MainApi {
    //запрос одного продукта
    @GET("auth/products/{id}")
    suspend fun getProductById(@Part("id") id: Int): Product

    // запрос аутефикации username и password
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User

    //получить все продукты
    @GET("auth/products")
    suspend fun getAllProducts(): Products

    //поиск продукта по https://dummyjson.com/products/search?q=phone
    // search и q
    @Headers("Content-Type': 'application/json")   //добавил статичиский @Headers и в низу кода динамический и передал токен
    @GET("auth/products/search")  //add auth/
    suspend fun getProductsByNameAuth(@Header("Authorization") token : String,@Query("q") name : String): Products
}