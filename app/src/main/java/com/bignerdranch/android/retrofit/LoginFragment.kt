package com.bignerdranch.android.retrofit

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.retrofit.databinding.FragmentLoginBinding
import com.bignerdranch.android.retrofit.retrofit.MainApi
import com.bignerdranch.android.retrofit.retrofit.authentication.AuthRequest
import com.bignerdranch.android.retrofit.viewmodel.LoginViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var mainApi: MainApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()

        onClick()
    }

    private fun initRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
        retrofit.baseUrl("https://dummyjson.com")
        retrofit.addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        mainApi = retrofit.build().create(MainApi::class.java)
    }

    //АВТОРИТИЗАЦИЯ
    private fun auth(authRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainApi.auth(authRequest)
            val message = response.errorBody()?.string().let {
                if (it != null) {
                    JSONObject(it).getString("message")
                }
            }
            requireActivity().runOnUiThread {
                binding.textErorr.text = message.toString()
                val user = response.body()
                if (user != null){
                    Picasso.get().load(user.image).into(binding.imageView)
                    binding.name.text = user.firstName
                    binding.buttonNext.visibility = View.VISIBLE
                    viewModel.token.value = user.token
                }
            }
        }
    }

    private fun onClick() {
        binding.apply {
            buttonNext.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_productsFragment)

            }
            buttonOk.setOnClickListener {
                auth(
                    AuthRequest(
                        login.text.toString(),
                        password.text.toString()
                    )
                )
            }
        }
    }
}