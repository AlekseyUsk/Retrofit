package com.bignerdranch.android.retrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val token = MutableLiveData<String>()
}