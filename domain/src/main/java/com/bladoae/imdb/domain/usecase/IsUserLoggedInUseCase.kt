package com.bladoae.imdb.domain.usecase

interface IsUserLoggedInUseCase {
    suspend operator fun invoke(): Boolean
}