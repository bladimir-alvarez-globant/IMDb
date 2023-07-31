package com.bladoae.imdb.requestmanager

import com.bladoae.imdb.requestmanager.model.TopRatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    suspend fun getPokemonList(
        @Query("api_key") apiKey: String
    ) : TopRatedResponse
}