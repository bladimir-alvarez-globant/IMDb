package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.domain.repository.MovieRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMovieByNameUseCaseImpl constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineContext
) : GetMovieByNameUseCase {

    override suspend fun invoke(name: String): Flow<List<Movie>?> {
        return flow {
            movieRepository.getMoviesByName(name)
                .collect { response ->
                    emit(response)
                }
        }.flowOn(dispatcher)
    }

}