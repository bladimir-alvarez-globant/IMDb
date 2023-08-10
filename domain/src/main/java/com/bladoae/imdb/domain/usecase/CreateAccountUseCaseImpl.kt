package com.bladoae.imdb.domain.usecase

import com.bladoae.imdb.domain.repository.UserRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreateAccountUseCaseImpl constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineContext
) : CreateAccountUseCase {

    override suspend fun invoke(email: String, password: String): Flow<Boolean> {
        return flow {
            userRepository.isEmailValid(email).collect { isEmailValid ->
                if(isEmailValid) {
                    userRepository.createAccount(email, password).collect {
                        emit(it)
                    }
                } else {
                    emit(false)
                }
            }
        }.flowOn(dispatcher)
    }

}