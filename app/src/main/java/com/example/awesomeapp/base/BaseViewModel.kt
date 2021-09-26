package com.example.awesomeapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
}