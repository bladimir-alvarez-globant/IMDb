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
import kotlinx.coroutines.test.runBlockingTest
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
    fun `when login response is success`() = runBlockingTest {
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
    fun `when login response is error`() = runBlockingTest {
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

    @Test
    fun `when createAccount response is success`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "1234"
        val expectedResponse = true
        coEvery {
            userAuthentication.createAccount(email, password)
        } returns flowOf(true)

        var actualResponse = false

        launch {
            userRepositoryImpl.createAccount(email, password).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.createAccount(email, password) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

    @Test
    fun `when createAccount response is error`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "1234"
        val expectedResponse = false
        coEvery {
            userAuthentication.createAccount(email, password)
        } returns flowOf(false)

        var actualResponse = true

        launch {
            userRepositoryImpl.createAccount(email, password).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.createAccount(email, password) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

    @Test
    fun `when isEmailValid response is success`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "1234"
        val expectedResponse = true
        coEvery {
            userAuthentication.isEmailValid(email)
        } returns flowOf(true)

        var actualResponse = false

        launch {
            userRepositoryImpl.isEmailValid(email).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.isEmailValid(email) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

    @Test
    fun `when isEmailValid response is error`() = runBlockingTest {
        val email = "blad@gmail.com"
        val password = "1234"
        val expectedResponse = false
        coEvery {
            userAuthentication.isEmailValid(email)
        } returns flowOf(false)

        var actualResponse = true

        launch {
            userRepositoryImpl.isEmailValid(email).collect {
                actualResponse = it
            }
        }

        coVerify(exactly = 1) { userAuthentication.isEmailValid(email) }
        assertEquals(
            expectedResponse,
            actualResponse
        )
    }

}