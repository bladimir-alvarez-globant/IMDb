package com.bladoae.imdb.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.data.MainCoroutineRule
import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.mappers.toMovie
import com.bladoae.imdb.data.mappers.toMovieEntity
import com.bladoae.imdb.data.mappers.toTopRated
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.requestmanager.model.MovieDto
import com.bladoae.imdb.requestmanager.model.TopRatedResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    @MockK
    private lateinit var movieApiService: MovieApiService

    @MockK
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepositoryImpl = MovieRepositoryImpl(movieApiService, movieDao, Dispatchers.IO)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get top rated movies response is success`() = runBlocking {
        val movieTitle = "Transformers"
        val movie = MovieDto(
            id = 1000,
            title = "Transformers"
        )
        val movies = listOf(
            movie
        )
        val expectedResponse = Resource.Success(
            TopRatedResponse(
                page = 1,
                results = movies,
                totalPages = 1,
                totalResults = 1
            )
        )

        val movieEntity = movie.toMovie().toMovieEntity()
        coEvery {
            movieDao.insertMovie(listOf(movieEntity))
        } returns Unit

        coEvery {
            movieApiService.getTopRatedMovies()
        } returns flowOf(expectedResponse)

        var actualResponse = TopRated()
        launch {
            movieRepositoryImpl.getTopRatedMovies()
                .collect { response -> actualResponse = response.data ?: TopRated() }
        }

        coVerify(exactly = 1) { movieApiService.getTopRatedMovies() }
        assertEquals(
            expectedResponse.data?.toTopRated(),
            actualResponse
        )
        assertEquals(
            "Name must be $movieTitle.",
            actualResponse.results?.first()?.title,
            movieTitle
        )
    }

}