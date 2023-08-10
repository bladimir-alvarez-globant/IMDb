package com.bladoae.imdb.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.domain.usecase.GetMovieByNameUseCase
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import com.bladoae.imdb.presentation.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @MockK
    private lateinit var getMovieByNameUseCase: GetMovieByNameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(
            getTopRatedMoviesUseCase,
            getMovieByNameUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getTopRatedMovies response is success`() = runBlockingTest {
        val movie = Movie(
            title = "The Shawshank Redemption",
            releaseDate = "2019",
            overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            posterPath = "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg"
        )
        val data = TopRated(
            results = listOf(
                movie
            )
        )
        val expectedResponse = Resource.Success(data)
        coEvery {
            getTopRatedMoviesUseCase()
        } returns flowOf(expectedResponse)

        viewModel.getTopRatedMovies()
        viewModel.topRatedMovies.observeForever {}

        val actualResponse = viewModel.topRatedMovies.value

        assertEquals(
            expectedResponse.data,
            actualResponse?.data
        )
        assertEquals(
            "The Shawshank Redemption",
            expectedResponse.data?.results?.first()?.title,
        )
    }

    @Test
    fun `when getMovieByName response is success`() = runBlockingTest {
        val name = "The Shawshank Redemption"
        val movie = Movie(
            title = "The Shawshank Redemption",
            releaseDate = "2019",
            overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            posterPath = "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg"
        )
        val expectedResponse = listOf(
            movie
        )

        coEvery {
            getTopRatedMoviesUseCase()
        } returns flowOf(Resource.Success(TopRated(results = expectedResponse)))

        coEvery {
            getMovieByNameUseCase(name)
        } returns flowOf(expectedResponse)

        viewModel.getMovieByName(name)
        viewModel.topRatedMovies.observeForever {}

        val actualResponse = viewModel.topRatedMovies.value

        assertEquals(
            expectedResponse,
            actualResponse?.data?.results
        )
        assertEquals(
            "The Shawshank Redemption",
            expectedResponse.first().title,
        )
    }

}