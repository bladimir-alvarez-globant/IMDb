package com.bladoae.imdb.di

import com.bladoae.imdb.BuildConfig
import com.bladoae.imdb.domain.repository.MovieRepository
import com.bladoae.imdb.domain.repository.UserRepository
import com.bladoae.imdb.domain.usecase.CreateAccountUseCase
import com.bladoae.imdb.domain.usecase.CreateAccountUseCaseImpl
import com.bladoae.imdb.domain.usecase.GetMovieByNameUseCase
import com.bladoae.imdb.domain.usecase.GetMovieByNameUseCaseImpl
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCaseImpl
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCase
import com.bladoae.imdb.domain.usecase.IsUserLoggedInUseCaseImpl
import com.bladoae.imdb.domain.usecase.LoginUserUseCase
import com.bladoae.imdb.domain.usecase.LoginUserUseCaseImpl
import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.bladoae.imdb.usermanager.UserAuthenticationImpl
import com.bladoae.imdb.utils.KotlinJsonAdapterFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
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
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    @Named("baseImageUrl")
    fun baseImageUrlProvider(): String = BuildConfig.BASE_IMAGE_URL

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

    @Provides
    @Singleton
    fun provideGetMovieByNameUseCase(
        movieRepository: MovieRepository,
        dispatcher: CoroutineContext
    ): GetMovieByNameUseCase = GetMovieByNameUseCaseImpl(movieRepository, dispatcher)

    @Provides
    @Singleton
    fun provideCreateAccountUseCase(
        userRepository: UserRepository,
        dispatcher: CoroutineContext
    ): CreateAccountUseCase = CreateAccountUseCaseImpl(userRepository, dispatcher)

}