package com.example.awesomeapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

open class BaseViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Throwable>()

    open fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}