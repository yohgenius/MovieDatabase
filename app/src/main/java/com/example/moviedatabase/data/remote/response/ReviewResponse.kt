package com.example.moviedatabase.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int,
    @field:SerializedName("results")
    val results: List<ReviewResult>

)

data class ReviewResult(
    @field:SerializedName("author")
    val author: String,
    @field:SerializedName("content")
    val content: String,
    @field:SerializedName("created_at")
    val created_at: String,
)
