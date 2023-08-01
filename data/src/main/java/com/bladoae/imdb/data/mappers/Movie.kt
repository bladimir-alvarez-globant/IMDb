package com.bladoae.imdb.data.mappers

import com.bladoae.imdb.databasemanager.entities.MovieEntity
import com.bladoae.imdb.domain.model.Movie

fun Movie.toMovieEntity() = MovieEntity(
    id ?: 0,
    adult,
    backdropPath,
    genreIds,
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

fun List<Movie>.toMovieEntityList() = map(Movie::toMovieEntity)