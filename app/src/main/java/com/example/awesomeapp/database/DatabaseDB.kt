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
                private var INSATANCE: DatabaseDB? = null
                fun getDatabase(context: Context): DatabaseDB{
                        return INSATANCE ?: synchronized(this){
                                val instancae = Room.databaseBuilder(
                                        context.applicationContext,
                                        DatabaseDB::class.java,
                                        "databaseku"
                                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                                INSATANCE = instancae
                                instancae
                        }
                }
        }

        abstract fun  getPhotoDao(): PhotoDao
}
