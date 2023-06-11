package com.example.moviedatabase.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.utils.Resource

interface UseCase {
    fun getDetailMovie(id: String): LiveData<Resource<DetailMovieEntity>>
    fun getMovieList(query: String): LiveData<PagingData<MovieModel>>
    fun setFavorite(data: DetailMovieEntity, state: Boolean)
    fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>>
    fun getReviewList(query: String): LiveData<PagingData<ReviewModel>>
}