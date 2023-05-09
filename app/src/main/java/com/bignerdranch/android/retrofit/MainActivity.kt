package com.bignerdranch.android.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.retrofit.databinding.ActivityMainBinding
import com.bignerdranch.android.retrofit.retrofit.MainApi
import com.bignerdranch.android.retrofit.retrofit.authentication.AuthRequest
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
        retrofit.baseUrl("https://dummyjson.com")  //указываем url домена
        retrofit.addConverterFactory(
            GsonConverterFactory.create()).build()

        val mainApi = retrofit.build().create(MainApi::class.java)

        binding.button.setOnClickListener {
            //корутины применил
            CoroutineScope(Dispatchers.IO).launch {
                // в функцию auth - запрос которая ,передаем заполненный AuthRequest xml editText
                val user = mainApi.auth(
                    AuthRequest(
                    binding.userName.text.toString(),
                    binding.password.text.toString()
                ))
                // вызвав основной поток использую Picasso так как нам нужно третий элемент картинку
                runOnUiThread{
                    binding.apply {
                        Picasso.get().load(user.image).into(imageViewActivity)
                        firstName.text = user.firstName // заполняю куда выводить полученный результат
                        lastName.text = user.lastName
                    }
                }
            }
        }
    }
}