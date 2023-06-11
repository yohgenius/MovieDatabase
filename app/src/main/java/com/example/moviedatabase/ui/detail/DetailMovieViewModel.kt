package com.example.moviedatabase.ui.detail

import androidx.lifecycle.*
import com.example.moviedatabase.data.Repository
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.domain.usecase.UseCase
import com.example.moviedatabase.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val useCase: UseCase,
    private val repository: Repository
) : ViewModel() {
    private val _id = MutableLiveData<String>()
    val getDetail: LiveData<Resource<DetailMovieEntity>> =
        Transformations.switchMap(_id) { id ->
            useCase.getDetailMovie(id)
        }

    fun setDetailId(id: String) {
        _id.value = id
    }

    fun setFavorite() {
        val resource = getDetail.value
        if (resource != null) {
            val detailMovie = resource.data
            if (detailMovie != null) {
                val newState = !detailMovie.isFavorite
                useCase.setFavorite(detailMovie, newState)
            }
        }
    }

    fun getTrailerLink(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getVideoList(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error occurred"))
        }
    }
}
