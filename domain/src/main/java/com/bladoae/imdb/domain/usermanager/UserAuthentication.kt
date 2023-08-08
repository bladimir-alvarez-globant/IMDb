package com.bladoae.imdb.domain.usermanager

interface UserAuthentication {
    fun login(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit)
}