package com.example.moviedatabase.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.domain.RepositoryInterface
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.external.utils.Resource
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: RepositoryInterface) : UseCase {

    override fun getDetailMovie(id: String): LiveData<Resource<DetailMovieEntity>> {
        return repository.getDetailMovie(id)
    }

    override fun getMovieList(query: String): LiveData<PagingData<MovieModel>> {
        return repository.getMovieList(query)
    }

    override fun setFavorite(data: DetailMovieEntity, state: Boolean) {
        return repository.setFavoriteMovie(data, state)
    }

    override fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>> {
        return repository.getFavoriteMovie()
    }

    override fun getReviewList(query: String): LiveData<PagingData<ReviewModel>> {
        return repository.getReviewList(query)
    }
}