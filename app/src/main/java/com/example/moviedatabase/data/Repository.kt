package com.example.moviedatabase.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviedatabase.data.local.LocalDataSource
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.data.remote.RemoteDataSource
import com.example.moviedatabase.data.remote.ReviewDataSource
import com.example.moviedatabase.data.remote.network.ApiResponse
import com.example.moviedatabase.data.remote.network.ApiService
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.domain.RepositoryInterface
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.external.utils.AppExecutors
import com.example.moviedatabase.external.utils.DataMapper
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val apiService: ApiService,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : RepositoryInterface {
    override fun getMovieList(query: String): LiveData<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ), pagingSourceFactory = { RemoteDataSource(apiService, query) }
        ).liveData
    }

    suspend fun getGenreList() = apiService.getGenreList()

    suspend fun getVideoList(id: String) = apiService.getMovieTrailer(id)

    override fun getDetailMovie(id: String) =
        object : NetworkBoundResource<DetailMovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailMovieEntity> {
                return localDataSource.getDetailMovie(id)
            }

            override fun shouldFetchData(data: DetailMovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override fun saveCallResult(data: DetailMovieResponse) {
                val movie = DataMapper.mapDetailResponseToEntities(data)
                localDataSource.insertDetailMovie(movie)
            }
        }.asLiveData()

    override fun setFavoriteMovie(data: DetailMovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(data, state) }
    }

    override fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>> {
        return localDataSource.getFavoriteMovie()
    }
    override fun getReviewList(query: String): LiveData<PagingData<ReviewModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ), pagingSourceFactory = { ReviewDataSource(apiService, query) }
        ).liveData
    }

}