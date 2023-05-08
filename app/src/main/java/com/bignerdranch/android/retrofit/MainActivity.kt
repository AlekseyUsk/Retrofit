package com.bignerdranch.android.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.retrofit.retrofit.ProductApi
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
        retrofit.baseUrl("https://dummyjson.com")  //указываем url домена
        retrofit.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient()
                    .create() //НЕИЗМЕННАЯ СТРОКА ВСЕГДА БУДЕМ ЕЕ КОПИРОВАТЬ И ВСТАВЛЯТЬ
            )
        )
        retrofit.build()

        //теперь слепленный Retrofit добавляем к ссылке которую будем использовать
        // ProductApi - это интерфейс запросов

        val productApi = retrofit.build().create(ProductApi::class.java)
        //корутины применил
        CoroutineScope(Dispatchers.IO).launch {
        val product = productApi.getProductById(3)  //Вызов ссылки
        }
    }
}