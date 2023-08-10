package com.bladoae.imdb.domain.repository

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.domain.model.TopRated
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopRatedMovies() : Flow<Resource<TopRated>>
    suspend fun getMoviesByName(name: String) : Flow<List<Movie>?>
}