package com.example.awesomeapp.view

interface BaseView {
    fun loading()
    fun doneLoading()
    fun onSuccess()
    fun onFailed()
}