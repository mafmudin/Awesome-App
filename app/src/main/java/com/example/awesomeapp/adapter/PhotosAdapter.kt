package com.example.awesomeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.awesomeapp.R
import com.example.awesomeapp.model.Photo
import kotlinx.android.synthetic.main.rv_item_grid_photo.view.*

class PhotosAdapter(var viewType : Int, private val listener: LocationAdapterListener)
    : RecyclerView.Adapter<PhotosAdapter.BaseViewHolder<*>>(){
    var list: MutableList<Photo> = arrayListOf()

    fun updateList(list: MutableList<Photo>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun additem(list: MutableList<Photo>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun updateViewType(viewType: Int){
        this.viewType = viewType
        notifyDataSetChanged()
    }

    inner class GridHolder(itemView: View)
        : BaseViewHolder<Photo>(itemView) {

        override fun bind(data: Photo) {
            with(itemView) {
                Glide.with(this)
                    .load(data.src.tiny)
                    .into(ivPhoto)

                tvTitle.text = data.photographer
                itemView.setOnClickListener {
                    listener.onClickList(data, position)
                }
            }
        }
    }

    inner class ListHolder(itemView: View)
        : BaseViewHolder<Photo>(itemView) {

        override fun bind(data: Photo) {
            with(itemView) {
                Glide.with(this).load(data.src.tiny).into(ivPhoto)
                tvTitle.text = data.photographer
                itemView.setOnClickListener {
                    listener.onClickList(data, position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewType == 0){
            0
        }else{
            1
        }
    }

    interface LocationAdapterListener{
        fun onClickList(data: Photo, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0){
            val view = inflater.inflate(R.layout.rv_item_grid_photo, parent, false)
            GridHolder(view)
        }else{
            val view = inflater.inflate(R.layout.rv_item_list_photo, parent, false)
            ListHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract class BaseViewHolder<T> (itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(item: T)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val data = list[position]
        when(holder){
            is GridHolder -> holder.bind(data)
            is ListHolder -> holder.bind(data)
        }
    }
}