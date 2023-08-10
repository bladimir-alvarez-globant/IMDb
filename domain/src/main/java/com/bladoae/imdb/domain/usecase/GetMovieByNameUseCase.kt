package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieByNameUseCase {
    suspend operator fun invoke(name: String): Flow<List<Movie>?>
}