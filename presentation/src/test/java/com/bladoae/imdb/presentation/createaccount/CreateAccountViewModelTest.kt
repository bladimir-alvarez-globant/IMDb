package com.bladoae.imdb.presentation.createaccount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.domain.usecase.CreateAccountUseCase
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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateAccountViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CreateAccountViewModel

    @MockK
    private lateinit var createAccountUseCase: CreateAccountUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CreateAccountViewModel(
            createAccountUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when createAccount response is success`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "123"
        val expectedResponse = true
        coEvery {
            createAccountUseCase(email, password)
        } returns flowOf(true)

        viewModel.createAccount(email, password)
        viewModel.createAccount.observeForever {}

        val actualResponse = viewModel.createAccount.value

        Assert.assertEquals(expectedResponse, actualResponse?.data)
    }

}