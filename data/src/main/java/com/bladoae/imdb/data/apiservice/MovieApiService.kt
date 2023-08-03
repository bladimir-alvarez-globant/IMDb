package com.bladoae.imdb.data.apiservice

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.requestmanager.model.TopRatedResponse
import kotlinx.coroutines.flow.Flow

interface MovieApiService {
    suspend fun getTopRatedMovies() : Flow<Resource<TopRatedResponse>>
}