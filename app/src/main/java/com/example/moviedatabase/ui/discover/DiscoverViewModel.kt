package com.example.moviedatabase.ui.discover

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.moviedatabase.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val useCase: UseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val genreId: String = savedStateHandle["genreId"]!!
    private val currentQuery = MutableLiveData(genreId)

    val movie = currentQuery.switchMap { currentQuery ->
        useCase.getMovieList(currentQuery).cachedIn(viewModelScope)
    }
}