package com.bladoae.imdb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.usecase.GetMovieByNameUseCase
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMovieByNameUseCase: GetMovieByNameUseCase
) : ViewModel() {

    private val _topRatedMovies = MutableLiveData<Resource<TopRated>>()
    val topRatedMovies: LiveData<Resource<TopRated>> by lazy { _topRatedMovies.apply { getTopRatedMovies() } }

    private fun getTopRatedMovies() {
        _topRatedMovies.value = Resource.Loading()
        viewModelScope.launch {
            getTopRatedMoviesUseCase().collect { response ->
                withContext(Dispatchers.Main) {
                    _topRatedMovies.value = response
                }
            }
        }
    }

    fun getMovieByName(name: String) {
        viewModelScope.launch {
            getMovieByNameUseCase(name)
                .collect { response ->
                    _topRatedMovies.value = Resource.Success(
                        TopRated(
                            results = response
                        )
                    )
                }
        }
    }

    fun onRetry() {
        getTopRatedMovies()
    }

}