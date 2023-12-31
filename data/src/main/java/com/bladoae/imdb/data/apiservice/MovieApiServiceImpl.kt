package com.bladoae.imdb.data.apiservice

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.requestmanager.ApiService
import com.bladoae.imdb.requestmanager.model.TopRatedResponse
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieApiServiceImpl @Inject constructor(
    private val service: ApiService,
    @Named("apiKey") private val apiKey: String,
) : MovieApiService {
    override suspend fun getTopRatedMovies(): Flow<Resource<TopRatedResponse>> {
        return flow {
            val response = service.getTopRatedMovies(apiKey)
            emit(Resource.Success(response))
        }
    }
}