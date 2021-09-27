package com.example.awesomeapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.awesomeapp.database.DatabaseDB
import com.example.awesomeapp.database.PhotoDao
import com.example.awesomeapp.model.Photo
import com.google.gson.Gson
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseDBTest: TestCase(){
    lateinit var databaseDB: DatabaseDB
    lateinit var dao: PhotoDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseDB = Room.inMemoryDatabaseBuilder(context, DatabaseDB::class.java).build()
        dao = databaseDB.getPhotoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        databaseDB.close()
    }

    @Test
    fun write () = runBlocking {
        val photoJson = "{\n" +
                "            \"id\": 1906821,\n" +
                "            \"width\": 4912,\n" +
                "            \"height\": 7360,\n" +
                "            \"url\": \"https://www.pexels.com/photo/cold-fashion-people-woman-1906821/\",\n" +
                "            \"photographer\": \"Alessio Cesario\",\n" +
                "            \"photographer_url\": \"https://www.pexels.com/@alessio-cesario-975080\",\n" +
                "            \"photographer_id\": 975080,\n" +
                "            \"avg_color\": \"#C1914E\",\n" +
                "            \"src\": {\n" +
                "                \"original\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg\",\n" +
                "                \"large2x\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
                "                \"large\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
                "                \"medium\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
                "                \"small\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
                "                \"portrait\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
                "                \"landscape\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
                "                \"tiny\": \"https://images.pexels.com/photos/1906821/pexels-photo-1906821.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280\"\n" +
                "            },\n" +
                "            \"liked\": false\n" +
                "        }"
        val photo = Gson().fromJson(photoJson, Photo::class.java)
        dao.insert(photo)

        val photos = dao.getPhotos(1).value!!
        assertTrue(photos.contains(photo))
    }
}