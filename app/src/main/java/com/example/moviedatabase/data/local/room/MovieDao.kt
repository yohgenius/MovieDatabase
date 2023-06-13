package com.example.moviedatabase.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedatabase.data.local.entity.DetailMovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(entity: List<DetailMovieEntity>)

    @Query("Select * From detail_movie_table Where id = :id")
    fun getDetailMovie(id: String): LiveData<DetailMovieEntity>

    @Update
    fun updateMovie(data: DetailMovieEntity)

    @Query("Select * From detail_movie_table Where favorite = 1")
    fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>>

}