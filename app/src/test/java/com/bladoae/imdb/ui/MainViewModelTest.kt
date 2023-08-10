package com.bladoae.imdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.MainCoroutineRule
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(
            isUserLoggedInUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when isUserLoggedIn response is success`() = runBlockingTest {
        val expectedResponse = true
        coEvery {
            isUserLoggedInUseCase()
        } returns true

        viewModel.isUserLoggedIn()
        viewModel.login.observeForever {}

        val actualResponse = viewModel.login.value

        Assert.assertEquals(expectedResponse, actualResponse?.data)
    }

}