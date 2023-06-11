package com.example.moviedatabase.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("backdrop_path")
    val backdropPath: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("genres")
    val genres: List<Genres>

)

data class Genres(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
)