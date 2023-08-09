package com.bladoae.imdb.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bladoae.imdb.data.MainCoroutineRule
import com.bladoae.imdb.domain.usermanager.UserAuthentication
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
class UserRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @MockK
    private lateinit var userAuthentication: UserAuthentication

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepositoryImpl = UserRepositoryImpl(userAuthentication)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when login response is success`() = runBlocking {
        val email = "blad@gmail.com"
        val password = "1234567"
        val expectedResponse = true
        coEvery {
            userAuthentication.login(email, password)
        } returns flowOf(true)

        var actualResponse = false

        launch {
            userRepositoryImpl.login(email, password).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.login(email, password) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

    @Test
    fun `when login response is error`() = runBlocking {
        val email = "blad@gmail.com"
        val password = "1234"
        val expectedResponse = false
        coEvery {
            userAuthentication.login(email, password)
        } returns flowOf(false)

        var actualResponse = true

        launch {
            userRepositoryImpl.login(email, password).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.login(email, password) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

}