package com.bignerdranch.android.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.retrofit.adapter.ProductAdapter
import com.bignerdranch.android.retrofit.databinding.ActivityMainBinding
import com.bignerdranch.android.retrofit.retrofit.MainApi
import com.bignerdranch.android.retrofit.retrofit.authentication.AuthRequest
import com.bignerdranch.android.retrofit.retrofit.authentication.User
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

        supportActionBar?.title = "Гость"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter()
        binding.recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
        retrofit.baseUrl("https://dummyjson.com")
        retrofit.addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val mainApi = retrofit.build().create(MainApi::class.java)

        var user: User? = null  //user это дата класс получения данных который я ранее создавал

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val objectProductsList = newText?.let {
                        mainApi.getProductsByNameAuth(
                            user?.token ?: "",
                            it
                        )
                    }
                    runOnUiThread {
                        binding.apply {
                            if (objectProductsList != null) {
                                adapter.submitList(objectProductsList.products)
                            }
                        }
                    }
                }
                return true
            }
        })
    }
}