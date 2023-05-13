package com.bignerdranch.android.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.retrofit.adapter.ProductAdapter
import com.bignerdranch.android.retrofit.databinding.FragmentLoginBinding
import com.bignerdranch.android.retrofit.databinding.FragmentProductsBinding
import com.bignerdranch.android.retrofit.retrofit.MainApi
import com.bignerdranch.android.retrofit.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var mainApi: MainApi
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        initRecyclerView()
        viewModel.token.observe(viewLifecycleOwner) { token ->
            CoroutineScope(Dispatchers.IO).launch {
                var list = mainApi.getAllProducts(token)
                requireActivity().runOnUiThread {
                    adapter.submitList(list.products)
                }
            }
        }
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

    private fun initRecyclerView() = with(binding) {
        adapter = ProductAdapter()
        recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewProducts.adapter = adapter
    }
}