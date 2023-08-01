package com.bladoae.imdb.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.base.test.MainCoroutineRule
import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetTopRatedMoviesUseCaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    private val dispatcher = Dispatchers.Main

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get top rated movies response is success`() = runBlocking {
        val apiKey = "11111"
        val expectedResponse = Resource.Success(
            TopRated(
                page = 1,
                results = listOf(
                    Movie(
                       id = 1000,
                       title = "Transformers"
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )
        )

        coEvery {
            movieRepository.getTopRatedMovies(apiKey)
        } returns flowOf(expectedResponse)

        var actualResponse = TopRated()
        launch(dispatcher) {
            getTopRatedMoviesUseCase(apiKey)
                .collect { response -> actualResponse = response.data ?: TopRated() }
        }

        coVerify(exactly = 1) { movieRepository.getTopRatedMovies(apiKey) }
        TestCase.assertEquals(
            expectedResponse.data,
            actualResponse
        )
    }

}