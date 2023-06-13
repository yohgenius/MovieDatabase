package com.example.moviedatabase.ui.home

import androidx.lifecycle.*
import com.example.moviedatabase.data.Repository
import com.example.moviedatabase.external.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getGenre() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getGenreList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error occurred"))
        }
    }
}