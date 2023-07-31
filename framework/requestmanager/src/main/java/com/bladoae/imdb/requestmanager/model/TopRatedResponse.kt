package com.bladoae.imdb.requestmanager.model

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)