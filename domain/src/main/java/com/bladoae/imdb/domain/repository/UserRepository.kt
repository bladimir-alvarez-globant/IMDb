package com.bladoae.imdb.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String) : Flow<Boolean>
    suspend fun isUserLoggedIn(): Boolean
}