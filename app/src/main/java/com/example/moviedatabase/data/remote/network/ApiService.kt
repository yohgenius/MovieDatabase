package com.example.moviedatabase.data.remote.network

import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.remote.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    suspend fun getGenreList(): Response<GenreResponse>

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieList(
        @Query("with_genres") query: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    fun getDetailMovie(
        @Path("id") id: String,
    ): Call<DetailMovieResponse>

    @GET("movie/{id}/videos?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieTrailer(
        @Path("id") id: String,
    ): Response<VideoResponse>
    @GET("movie/{id}/reviews?api_key=${BuildConfig.API_KEY}")
    suspend fun getReviewList(
        @Path("id") id: String,
    ): ReviewResponse
}