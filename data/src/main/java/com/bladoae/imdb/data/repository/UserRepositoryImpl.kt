package com.bladoae.imdb.data.repository

import com.bladoae.imdb.domain.repository.UserRepository
import com.bladoae.imdb.domain.usermanager.UserAuthentication
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAuthentication: UserAuthentication
) : UserRepository {

    override suspend fun login(email: String, password: String) = userAuthentication.login(email, password)

    override suspend fun isUserLoggedIn() = userAuthentication.isUserLoggedIn()

}