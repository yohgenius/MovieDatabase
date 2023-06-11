package com.example.moviedatabase.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("results")
    val results: List<VideoResult>
)

data class VideoResult(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("site")
    val site: String,
)