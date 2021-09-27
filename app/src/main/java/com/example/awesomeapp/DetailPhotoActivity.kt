package com.example.awesomeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.awesomeapp.base.BaseActivity
import com.example.awesomeapp.model.Photo
import kotlinx.android.synthetic.main.activity_detail_photo.*
import timber.log.Timber

class DetailPhotoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_photo)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val data = intent.getSerializableExtra("data") as Photo

        supportActionBar?.title = data.photographer

        Timber.e("INTENT DATA %s", data)

        Glide.with(this)
            .load(data.src.original)
            .thumbnail(Glide.with(this).load(R.drawable.loading))
            .into(ivDetail)

        if (!isInternetConnected(this)){
            tvImageNotLoaded.visibility = View.VISIBLE
        }else{
            tvImageNotLoaded.visibility = View.GONE
        }

        tvPhotographerName.text = String.format("Photographer Name \n%s", data.photographer)
        tvPhotoUrl.text = String.format("Photo url \n%s",data.url)

        tvPhotoUrl.movementMethod = LinkMovementMethod.getInstance()
        tvPhotoUrl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            startActivity(browserIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}