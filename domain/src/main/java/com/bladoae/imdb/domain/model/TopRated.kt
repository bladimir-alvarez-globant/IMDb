package com.bladoae.imdb.domain.model

data class TopRated(
    val page: Int? = null,
    val results: List<Movie>? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
)