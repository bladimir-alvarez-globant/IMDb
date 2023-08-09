package com.bladoae.imdb.domain.usecase

import kotlinx.coroutines.flow.Flow

interface LoginUserUseCase {
    suspend operator fun invoke(email: String, password: String) : Flow<Boolean>
}