package com.bladoae.imdb.data.repository

import com.bladoae.imdb.domain.repository.UserRepository
import com.bladoae.imdb.domain.usermanager.UserAuthentication
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAuthentication: UserAuthentication
) : UserRepository {

    override fun login(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit) {
        userAuthentication.login(email, password) { response ->
            callback.invoke(response)
        }
    }

    override fun isUserLoggedIn() = userAuthentication.isUserLoggedIn()

}