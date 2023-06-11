package com.example.moviedatabase.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.example.moviedatabase.data.remote.network.ApiResponse
import com.example.moviedatabase.data.remote.network.ApiService
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.utils.DataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val STARTING_PAGE_INDEX: Int = 1

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getMovieList(query, position)
            val result = DataMapper.mapMovieResponseToModel(response.items)

            LoadResult.Page(
                data = result,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    fun getDetailMovie(id: String): LiveData<ApiResponse<DetailMovieResponse>> {
        val user = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        apiService.getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    user.value = ApiResponse.success(response.body()!!)
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
        return user
    }
}