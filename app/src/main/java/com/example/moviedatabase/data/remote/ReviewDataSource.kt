package com.example.moviedatabase.data.remote

import androidx.paging.PagingSource
import com.example.moviedatabase.data.remote.network.ApiService
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.external.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val STARTING_PAGE_INDEX: Int = 1

@Singleton
class ReviewDataSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, ReviewModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewModel> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getReviewList(query)
            val result = DataMapper.mapReviewResponseToModel(response.results)

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
}