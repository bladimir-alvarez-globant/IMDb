package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.repository.UserRepository

class LoginUserUseCaseImpl constructor(
    private val userRepository: UserRepository
) : LoginUserUseCase {

    override operator fun invoke(email: String, password: String, callback: (isSuccessful: Boolean) -> Unit) {
        userRepository.login(email, password) {response ->
            callback.invoke(response)
        }
    }

}