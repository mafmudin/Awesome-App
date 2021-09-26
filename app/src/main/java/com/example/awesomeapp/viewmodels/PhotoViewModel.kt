package com.example.awesomeapp.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.awesomeapp.base.BaseViewModel
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.networking.Either
import com.example.awesomeapp.repository.PhotoRepository

class PhotoViewModel(private val repository: PhotoRepository): BaseViewModel() {
    val photoResult = MutableLiveData<MutableList<Photo>>()
    suspend fun insert(photo: Photo) = repository.insert(photo)
    suspend fun update(photo: Photo) = repository.update(photo)
    suspend fun delete(photo: Photo) = repository.delete(photo)
    suspend fun insertPhotos(photos: MutableList<Photo>) = repository.insertPhotos(photos)


    fun getPhoto(lifecycleOwner: LifecycleOwner, page:Int, api:String){
        isLoading.postValue(true)
        repository.getPhotos(page, api){
            isLoading.postValue(false)
            when(it){
                is Either.Left -> {
                    photoResult.postValue(it.left)
                    repository.insertPhotos(it.left!!)
                }
                is Either.Right -> {
                    repository.getPhoto(page).observe(lifecycleOwner, { it ->
                        photoResult.postValue(it)
                    })
                }
            }
        }
    }
}