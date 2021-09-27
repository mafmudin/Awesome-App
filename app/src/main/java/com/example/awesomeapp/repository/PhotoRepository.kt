package com.example.awesomeapp.repository

import com.example.awesomeapp.database.DatabaseDB
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.networking.Either
import com.example.awesomeapp.networking.RetrofitInstance
import com.example.awesomeapp.networking.parse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoRepository(private val databaseDB: DatabaseDB) {
    fun insert(photo: Photo) = databaseDB.getPhotoDao().insert(photo)
    fun update(photo: Photo) = databaseDB.getPhotoDao().update(photo)
    fun delete(photo: Photo) = databaseDB.getPhotoDao().delete(photo)
    fun getPhoto(index: Int) = databaseDB.getPhotoDao().getPhotos(index)
    fun insertPhotos(photos: MutableList<Photo>) = databaseDB.getPhotoDao().insertAll(photos)

    private val retrofitInstance = RetrofitInstance.create()
    fun getPhotos(page: Int, api:String, callBack: (Either<MutableList<Photo>?, Throwable>) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            val response = retrofitInstance.getPhotos(page, api)
            response.parse(
                {
                    callBack(Either.Left(it.photos))
                },{
                    it.printStackTrace()
                    callBack(Either.Right(it))
                }
            )
        }
    }
}