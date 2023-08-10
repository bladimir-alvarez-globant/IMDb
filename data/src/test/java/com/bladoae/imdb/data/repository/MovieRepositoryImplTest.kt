package com.bladoae.imdb.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.data.MainCoroutineRule
import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.mappers.toMovie
import com.bladoae.imdb.data.mappers.toMovieEntity
import com.bladoae.imdb.data.mappers.toMovieList
import com.bladoae.imdb.data.mappers.toTopRated
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.databasemanager.entities.MovieEntity
import com.bladoae.imdb.domain.model.Movie
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

    private val baseImageUrl = "baseImageUrl"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepositoryImpl = MovieRepositoryImpl(movieApiService, movieDao, Dispatchers.IO, baseImageUrl)
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

        val movieEntity = movie.toMovie(baseImageUrl).toMovieEntity()
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
            expectedResponse.data?.toTopRated(baseImageUrl),
            actualResponse
        )
        assertEquals(
            "Name must be $movieTitle.",
            actualResponse.results?.first()?.title,
            movieTitle
        )
    }

    @Test
    fun `when getMoviesByName response is success`() = runBlocking {
        val movieTitle = "Transformers"
        val movie = MovieEntity(
            id = 1000,
            title = "Transformers"
        )
        val expectedResponse = listOf(
            movie
        )

        coEvery {
            movieDao.selectMovie(movieTitle)
        } returns expectedResponse

        var actualResponse = listOf<Movie>()
        launch {
            movieRepositoryImpl.getMoviesByName(movieTitle)
                .collect { response -> actualResponse = response ?: listOf() }
        }

        coVerify(exactly = 1) { movieDao.selectMovie(movieTitle) }
        assertEquals(
            expectedResponse.toMovieList(),
            actualResponse
        )
        assertEquals(
            "Name must be $movieTitle.",
            actualResponse.first().title,
            movieTitle
        )
    }

}