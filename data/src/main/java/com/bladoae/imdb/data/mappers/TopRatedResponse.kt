package com.bladoae.imdb.data.mappers

import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.requestmanager.model.TopRatedResponse

fun TopRatedResponse.toTopRated() = TopRated(
    page,
    results?.toMovieList(),
    totalPages,
    totalResults
)