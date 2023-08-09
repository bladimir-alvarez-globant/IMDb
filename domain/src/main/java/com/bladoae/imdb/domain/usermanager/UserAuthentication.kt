package com.bladoae.imdb.domain.usermanager

import kotlinx.coroutines.flow.Flow

interface UserAuthentication {
    suspend fun login(email: String, password: String) : Flow<Boolean>
    suspend fun isUserLoggedIn(): Boolean
}