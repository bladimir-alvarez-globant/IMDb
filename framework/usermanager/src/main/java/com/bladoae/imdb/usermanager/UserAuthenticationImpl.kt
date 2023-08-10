package com.bladoae.imdb.usermanager

import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserAuthenticationImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserAuthentication {

    override suspend fun login(email: String, password: String): Flow<Boolean> {
        return flow {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(response.user != null)
        }
    }

    override suspend fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override suspend fun createAccount(email: String, password: String): Flow<Boolean> {
        return flow {
            val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(response.user != null)
        }
    }

    override suspend fun isEmailValid(email: String): Flow<Boolean> {
        return flow {
            val response = firebaseAuth.fetchSignInMethodsForEmail(email).await()
            emit(response.signInMethods?.isEmpty() ?: true)
        }
    }

}