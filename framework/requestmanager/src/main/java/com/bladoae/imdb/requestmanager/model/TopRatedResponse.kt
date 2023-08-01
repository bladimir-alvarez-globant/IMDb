package com.bladoae.imdb.requestmanager.model

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int? = null,
    val results: List<MovieDto>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)