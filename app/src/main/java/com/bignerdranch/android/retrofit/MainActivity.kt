package com.bignerdranch.android.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.retrofit.adapter.ProductAdapter
import com.bignerdranch.android.retrofit.databinding.ActivityMainBinding
import com.bignerdranch.android.retrofit.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Todo Подключаем Адаптер к Recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter() //проинициализировал адаптер
        binding.recyclerView.adapter = adapter //передал адаптер который проиницилиазировал

        // Todo подключаем Retrofit и делай запрос натполучение всех продуктов
        val retrofit = Retrofit.Builder()
        retrofit.baseUrl("https://dummyjson.com")  //указываем url домена
        retrofit.addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val mainApi = retrofit.build().create(MainApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val objectProductsList =
                mainApi.getAllProducts() //получаем мой обьект со списком продуктов с сервера
            runOnUiThread {
                binding.apply {
                    adapter.submitList(objectProductsList.products) //передаем в адаптер полученный список
                }
            }
        }
    }
}