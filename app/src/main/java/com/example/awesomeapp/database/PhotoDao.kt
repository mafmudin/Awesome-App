package com.example.awesomeapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.awesomeapp.model.Photo

@Dao
interface PhotoDao {
    @Insert(onConflict = REPLACE)
    fun insert(photo: Photo)

    @Insert(onConflict = REPLACE)
    fun insertAll(photo: MutableList<Photo>)

    @Update
    fun update(photo: Photo)

    @Delete
    fun delete(photo: Photo)

    @Query("SELECT * FROM Photo LIMIT 20 OFFSET :pageIndex")
    fun getPhotos(pageIndex: Int): LiveData<MutableList<Photo>>
}