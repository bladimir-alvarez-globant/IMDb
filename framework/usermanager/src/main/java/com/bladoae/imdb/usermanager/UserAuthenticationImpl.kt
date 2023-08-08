package com.bladoae.imdb.usermanager

import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserAuthenticationImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserAuthentication {

    override fun login(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            callback.invoke(it.isSuccessful)
        }
    }

    override fun isUserLoggedIn() = firebaseAuth.currentUser != null

}