package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import kotlinx.coroutines.flow.Flow

interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Flow<Resource<TopRated>>
}