package com.example.moviedatabase.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val useCase: UseCase): ViewModel(){
    fun getFavoriteMovie(): LiveData<List<DetailMovieEntity>> {
        return useCase.getFavoriteMovie()
    }

    fun setFavoriteMovie(data: DetailMovieEntity){
        val newState = !data.isFavorite
        useCase.setFavorite(data, newState)
    }
}