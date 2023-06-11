package com.example.moviedatabase.domain.model

import com.google.gson.annotations.SerializedName

data class MovieModel(

    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("genre_ids")
    val genre_ids: List<Int>
)