package com.bladoae.imdb.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String) : Flow<Boolean>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun createAccount(email: String, password: String) : Flow<Boolean>
    suspend fun isEmailValid(email: String): Flow<Boolean>
}