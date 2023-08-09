package com.bladoae.imdb.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCase
import com.bladoae.imdb.domain.usecase.LoginUserUseCase
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
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var loginUserUseCase: LoginUserUseCase

    @MockK
    private lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(
            loginUserUseCase,
            isUserLoggedInUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loginUser response is success`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "123"
        val expectedResponse = true
        coEvery {
            loginUserUseCase(email, password)
        } returns flowOf(true)

        viewModel.loginUser(email, password)
        viewModel.login.observeForever {}

        val actualResponse = viewModel.login.value

        assertEquals(expectedResponse, actualResponse?.data)
    }

}