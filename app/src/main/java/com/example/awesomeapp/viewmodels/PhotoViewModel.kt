package com.example.awesomeapp.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.awesomeapp.base.BaseViewModel
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.networking.Either
import com.example.awesomeapp.repository.PhotoRepository

class PhotoViewModel(private val repository: PhotoRepository): BaseViewModel() {
    val photoResult = MutableLiveData<MutableList<Photo>>()
    fun insert(photo: Photo) = repository.insert(photo)
    fun update(photo: Photo) = repository.update(photo)
    fun delete(photo: Photo) = repository.delete(photo)
    fun insertPhotos(photos: MutableList<Photo>) = repository.insertPhotos(photos)


    fun getPhoto(lifecycleOwner: LifecycleOwner, page:Int, api:String){
        isLoading.postValue(true)
        if (internetIsConnected()){
            repository.getPhotos(page, api){
                when(it){
                    is Either.Left -> {
                        isLoading.postValue(false)
                        photoResult.postValue(it.left)
                        repository.insertPhotos(it.left!!)
                    }
                    is Either.Right -> {
                        isLoading.postValue(false)
                        isError.postValue(it.right)
                    }
                }
            }
        }else{
            repository.getPhoto(page).observe(lifecycleOwner, {
                photoResult.postValue(it)
            })
        }

    }
}