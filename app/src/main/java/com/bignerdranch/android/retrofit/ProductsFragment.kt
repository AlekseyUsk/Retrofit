package com.bignerdranch.android.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bignerdranch.android.retrofit.databinding.FragmentLoginBinding
import com.bignerdranch.android.retrofit.viewmodel.LoginViewModel

class ProductsFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val viewModel : LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.token.observe(viewLifecycleOwner){ token ->

        }
    }
}