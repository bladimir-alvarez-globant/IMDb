package com.bladoae.imdb.data.repository

import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.mappers.toMovieEntityList
import com.bladoae.imdb.data.mappers.toMovieList
import com.bladoae.imdb.data.mappers.toTopRated
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val dispatcher: CoroutineContext,
    @Named("baseImageUrl") private val baseImageUrl: String
) : MovieRepository {
    override suspend fun getTopRatedMovies(): Flow<Resource<TopRated>> {
        return flow {
            movieApiService.getTopRatedMovies()
                .map { response ->
                    if(response is Resource.Success) {
                        return@map Resource.Success(
                            data = response.data?.toTopRated(baseImageUrl) ?: TopRated()
                        )
                    } else {
                        return@map Resource.Error<TopRated>(response.message ?: "")
                    }
                }
                .onEach { response ->
                    if(response is Resource.Success) {
                        response.data?.results?.toMovieEntityList()?.let { data ->
                            movieDao.insertMovie(data)
                        }
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(dispatcher)
    }

    override suspend fun getMoviesByName(name: String): Flow<List<Movie>?> {
        return flow {
            val response = movieDao.selectMovie(name)
            val movies = if (response.isNotEmpty()) {
                response.toMovieList()
            } else {
                null
            }
            emit(movies)
        }.flowOn(dispatcher)
    }
}