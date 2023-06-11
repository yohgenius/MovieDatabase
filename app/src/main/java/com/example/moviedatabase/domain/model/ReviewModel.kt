package com.example.moviedatabase.domain.model

import com.google.gson.annotations.SerializedName

data class ReviewModel(
    @field:SerializedName("author")
    val author: String,
    @field:SerializedName("content")
    val content: String,
    @field:SerializedName("created_at")
    val created_at: String,
)