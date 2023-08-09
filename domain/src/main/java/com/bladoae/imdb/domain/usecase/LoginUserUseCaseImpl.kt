package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.repository.UserRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUserUseCaseImpl constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineContext
) : LoginUserUseCase {

    override suspend operator fun invoke(email: String, password: String): Flow<Boolean> {
        return flow {
            coroutineScope {
                val isUserLoggedInAsync = async { userRepository.isUserLoggedIn() }
                val isUserLoggedIn = isUserLoggedInAsync.await()
                if(!isUserLoggedIn) {
                    userRepository.login(email, password).collect { response ->
                        emit(response)
                    }
                } else {
                    emit(true)
                }
            }
        }.flowOn(dispatcher)
    }

}