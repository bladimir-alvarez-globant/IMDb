package com.bladoae.imdb.data.mappers

import com.bladoae.imdb.databasemanager.entities.MovieEntity
import com.bladoae.imdb.domain.model.Movie

fun MovieEntity.toMovie() = Movie(
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

fun List<MovieEntity>.toMovieList() = map(MovieEntity::toMovie)