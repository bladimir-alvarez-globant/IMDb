package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.repository.MovieRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTopRatedMoviesUseCase(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineContext
) {
    suspend operator fun invoke(apiKey: String): Flow<Resource<TopRated>> {
        return flow {
            movieRepository.getTopRatedMovies(apiKey)
                .collect { response: Resource<TopRated> ->
                    emit(response)
                }
        }.flowOn(dispatcher)
    }
}