package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.repository.MovieRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTopRatedMoviesUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineContext
) : GetTopRatedMoviesUseCase {

    override suspend operator fun invoke(): Flow<Resource<TopRated>> {
        return flow {
            movieRepository.getTopRatedMovies()
                .collect { response: Resource<TopRated> ->
                    emit(response)
                }
        }.flowOn(dispatcher)
    }

}