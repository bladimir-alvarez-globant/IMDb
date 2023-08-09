package com.bladoae.imdb.di

import com.bladoae.imdb.BuildConfig
import com.bladoae.imdb.domain.repository.MovieRepository
import com.bladoae.imdb.domain.repository.UserRepository
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCaseImpl
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCase
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCaseImpl
import com.bladoae.imdb.domain.usecase.LoginUserUseCase
import com.bladoae.imdb.domain.usecase.LoginUserUseCaseImpl
import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.bladoae.imdb.usermanager.UserAuthenticationImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("baseBackendUrl")
    fun baseBackendUrlProvider(): String = BuildConfig.BASE_BE_URL

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(
        movieRepository: MovieRepository,
        dispatcher: CoroutineContext
    ): GetTopRatedMoviesUseCase = GetTopRatedMoviesUseCaseImpl(movieRepository, dispatcher)

    @Provides
    @Singleton
    fun provideUserAuthentication(
        firebaseAuth: FirebaseAuth
    ) : UserAuthentication {
        return UserAuthenticationImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginUserUseCase(
        userRepository: UserRepository,
        dispatcher: CoroutineContext
    ): LoginUserUseCase = LoginUserUseCaseImpl(userRepository, dispatcher)

    @Provides
    @Singleton
    fun provideIsUserLoggedInUseCaseUseCase(
        userRepository: UserRepository
    ): IsUserLoggedInUseCase = IsUserLoggedInUseCaseImpl(userRepository)

}