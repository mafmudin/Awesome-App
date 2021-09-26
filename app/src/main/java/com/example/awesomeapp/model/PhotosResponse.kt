package com.example.awesomeapp.model


import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val photos: MutableList<Photo>,
    @SerializedName("total_results")
    val totalResults: Int
)