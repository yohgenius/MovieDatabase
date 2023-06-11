package com.example.moviedatabase.data.remote.response

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<GenreResult>
)

data class GenreResult(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
)