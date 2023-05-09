package com.bignerdranch.android.retrofit.retrofit.authentication

// принимает данные
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String,
)
