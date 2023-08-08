package com.bladoae.imdb.domain.repository

interface UserRepository {
    fun login(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit)
}