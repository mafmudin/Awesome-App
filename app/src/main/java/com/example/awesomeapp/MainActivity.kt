package com.example.awesomeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
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

    var page = 1

    lateinit var llm: LinearLayoutManager

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
                val intent = Intent(this@MainActivity, DetailPhotoActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        })

        llm = GridLayoutManager(this@MainActivity, 2)
        rvPhotos.apply {
            layoutManager = llm
            adapter = photoAdapter
        }


        viewModel.apply {
            isLoading.observe(this@MainActivity, {
                if (it){
                    if (photoAdapter.viewType == 0){
                        gridShimmer.visibility = View.VISIBLE
                        gridShimmer.startShimmer()
                    }else{
                        listShimmer.visibility = View.VISIBLE
                        listShimmer.startShimmer()
                    }
                }else{
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
                if (page == 1){
                    photoAdapter.updateList(it)
                }else{
                    photoAdapter.additem(it)
                }

                if (it.isEmpty()){
                    llEmpty.visibility = View.VISIBLE
                }else{
                    llEmpty.visibility = View.GONE
                }
            })
        }
        viewModel.getPhoto(this, page, Constant.apiKey)
        nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight && isInternetConnected(this)){
                page ++
                viewModel.getPhoto(this@MainActivity, page, Constant.apiKey)
                Timber.e("PAGE %s", page)
            }
        })

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
                llm = GridLayoutManager(this@MainActivity, 2)
                rvPhotos.apply {
                    layoutManager = llm
                    adapter = photoAdapter
                }
            }

            R.id.menuList -> {
                llm = LinearLayoutManager(this@MainActivity)
                photoAdapter.updateViewType(1)
                rvPhotos.apply {
                    layoutManager = llm
                    adapter = photoAdapter
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}