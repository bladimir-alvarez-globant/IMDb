package com.bladoae.imdb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    @Named("apiKey") private val apiKey: String,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _topRatedMovies = MutableLiveData<Resource<TopRated>>()
    val topRatedMovies: LiveData<Resource<TopRated>> = _topRatedMovies

    private fun getTopRatedMovies() {
        viewModelScope.launch(dispatcher) {
            _topRatedMovies.value = Resource.Loading()
            getTopRatedMoviesUseCase(apiKey).collect { response ->
                _topRatedMovies.value = response
            }
        }
    }

}