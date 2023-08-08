package com.bladoae.imdb.usermanager

import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.google.firebase.auth.FirebaseAuth

class UserAuthenticationImpl : UserAuthentication {

    private var mAuth: FirebaseAuth? = null

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    override fun login(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit) {
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            callback.invoke(it.isSuccessful)
        }
    }

}