package com.example.awesomeapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomeapp.adapter.PhotosAdapter
import com.example.awesomeapp.base.BaseActivity
import com.example.awesomeapp.database.DatabaseDB
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.networking.Constant
import com.example.awesomeapp.repository.PhotoRepository
import com.example.awesomeapp.viewmodels.PhotoViewModel
import com.example.awesomeapp.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(){
    lateinit var databaseDB: DatabaseDB
    lateinit var repository: PhotoRepository
    lateinit var viewModel: PhotoViewModel
    lateinit var factory: ViewModelFactory
    lateinit var photoAdapter : PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        collapsingToolbar.title = "Awesome App"
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))

        databaseDB = DatabaseDB.getDatabase(this)
        repository = PhotoRepository(databaseDB)
        factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PhotoViewModel::class.java]

        photoAdapter = PhotosAdapter(0, object : PhotosAdapter.LocationAdapterListener{
            override fun onClickList(data: Photo, position: Int) {

            }

        })

        rvPhotos.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = photoAdapter
        }


        viewModel.apply {
            isLoading.observe(this@MainActivity, {
                if (it){
                    //TODO show dialog
                    if (photoAdapter.viewType == 0){
                        gridShimmer.visibility = View.VISIBLE
                        gridShimmer.startShimmer()
                    }else{
                        listShimmer.visibility = View.VISIBLE
                        listShimmer.startShimmer()
                    }
                }else{
                    //TODO dismiss dialog
                    if (photoAdapter.viewType == 0){
                        gridShimmer.visibility = View.GONE
                        gridShimmer.stopShimmer()
                    }else{
                        listShimmer.visibility = View.GONE
                        listShimmer.stopShimmer()
                    }
                }
            })

            photoResult.observe(this@MainActivity,{
                Timber.e("RESPONSE RESULT %s", it)
                photoAdapter.updateList(it)
            })
        }
        viewModel.getPhoto(this, 1, Constant.apiKey)
    }

    //TODO change menu color while selected
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuGrid -> {
                photoAdapter.updateViewType(0)
                rvPhotos.apply {
                    layoutManager = GridLayoutManager(this@MainActivity, 2)
                    adapter = photoAdapter
                }
            }

            R.id.menuList -> {
                photoAdapter.updateViewType(1)
                rvPhotos.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = photoAdapter
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}