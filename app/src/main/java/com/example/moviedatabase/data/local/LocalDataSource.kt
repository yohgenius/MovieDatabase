package com.example.moviedatabase.data.local

import androidx.lifecycle.LiveData
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.data.local.room.MovieDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun insertDetailMovie(detailUser: List<DetailMovieEntity>) =
        movieDao.insertUserDetail(detailUser)

    fun getDetailMovie(id: String) = movieDao.getDetailMovie(id)

    fun setFavoriteMovie(data: DetailMovieEntity, newState: Boolean) {
        data.isFavorite = newState
        movieDao.updateMovie(data)
    }

    fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>> {
        return movieDao.getFavoriteMovie()
    }

//    fun searchFavoriteMovie(query: String): LiveData<List<DetailMovieEntity>> {
//        return movieDao.searchMovie(query)
//    }
}