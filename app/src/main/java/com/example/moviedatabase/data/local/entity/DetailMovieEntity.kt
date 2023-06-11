package com.example.moviedatabase.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_movie_table")

data class DetailMovieEntity(
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false,

    @PrimaryKey
    val id: Int? = null,

    val title: String? = null,

    val poster_path: String? = null,

    val backdropPath: String? = null,

    val overview: String? = null,

)