package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.repository.UserRepository

class IsUserLoggedInUseCaseImpl constructor(
    private val userRepository: UserRepository
) : IsUserLoggedInUseCase {

    override suspend operator fun invoke() = userRepository.isUserLoggedIn()

}