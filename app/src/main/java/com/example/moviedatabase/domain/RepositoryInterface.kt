package com.example.moviedatabase.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.utils.Resource

interface RepositoryInterface {
    fun getMovieList(query: String): LiveData<PagingData<MovieModel>>
    fun getDetailMovie(id: String) : LiveData<Resource<DetailMovieEntity>>
    fun setFavoriteMovie(data: DetailMovieEntity, state: Boolean)
    fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>>

}