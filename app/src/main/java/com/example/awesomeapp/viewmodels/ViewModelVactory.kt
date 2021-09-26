package com.example.awesomeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.awesomeapp.repository.PhotoRepository
import java.lang.Exception

class ViewModelVactory(private val repository: PhotoRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val cons = modelClass.getDeclaredConstructor(PhotoRepository::class.java)
            return  cons.newInstance(repository)
        }catch (e: Exception){
            e.printStackTrace()
        }

        return super.create(modelClass)
    }
}