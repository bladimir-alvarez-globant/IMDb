package com.bladoae.imdb.data.mappers

import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.requestmanager.model.MovieDto

fun MovieDto.toMovie() = Movie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun List<MovieDto>.toMovieList() = map(MovieDto::toMovie)