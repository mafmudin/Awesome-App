package com.example.awesomeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.awesomeapp.model.Photo

@Database(
        entities = [Photo::class],
        version = 1, exportSchema = false
)

@TypeConverters()
abstract class DatabaseDB : RoomDatabase() {
        companion object{
                @Volatile
                private var INSTANCE: DatabaseDB? = null
                fun getDatabase(context: Context): DatabaseDB{
                        return INSTANCE ?: synchronized(this){
                                val instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        DatabaseDB::class.java,
                                        "databaseku"
                                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                                INSTANCE = instance
                                instance
                        }
                }
        }

        abstract fun  getPhotoDao(): PhotoDao
}
