package com.example.moviedatabase.utils

import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.MovieResult
import com.example.moviedatabase.data.remote.response.ReviewResult
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.domain.model.ReviewModel

object DataMapper {
    fun mapMovieResponseToModel(data: List<MovieResult>): List<MovieModel> {
        return data.map {
            with(it) {
                MovieModel(
                    id,
                    title,
                    posterPath,
                    genre_ids
                )
            }
        }
    }

    fun mapDetailResponseToEntities(data: DetailMovieResponse): List<DetailMovieEntity> {
        val movies = ArrayList<DetailMovieEntity>()
        val movie = DetailMovieEntity(
            false,
            data.id,
            data.title,
            data.posterPath,
            data.backdropPath,
            data.overview,
        )
        movies.add(movie)
        return movies
    }

    fun mapReviewResponseToModel(data: List<ReviewResult>): List<ReviewModel> {
        return data.map {
            with(it) {
                ReviewModel(
                    author, content, created_at
                )
            }
        }
    }
}