package com.bladoae.imdb.data.mappers

import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.requestmanager.model.MovieDto

fun MovieDto.toMovie(
    baseImageUrl: String
) = Movie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    "$baseImageUrl$posterPath",
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun List<MovieDto>.toMovieList(baseImageUrl: String) = map {
    it.toMovie(baseImageUrl)
}