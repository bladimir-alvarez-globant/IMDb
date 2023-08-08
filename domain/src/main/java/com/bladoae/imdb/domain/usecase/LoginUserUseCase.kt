package com.bladoae.imdb.domain.usecase

interface LoginUserUseCase {
    operator fun invoke(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit)
}