package com.example.moviedatabase.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int,
    @field:SerializedName("results")
    val items: List<MovieResult>
)

data class MovieResult(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("genre_ids")
    val genre_ids: List<Int>

)