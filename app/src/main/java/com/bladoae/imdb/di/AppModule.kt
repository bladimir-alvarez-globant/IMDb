package com.bladoae.imdb.di

import com.bladoae.imdb.BuildConfig
import com.bladoae.imdb.domain.repository.MovieRepository
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCase
import com.bladoae.imdb.domain.usecase.GetTopRatedMoviesUseCaseImpl
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
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(
        movieRepository: MovieRepository,
        dispatcher: CoroutineContext
    ): GetTopRatedMoviesUseCase = GetTopRatedMoviesUseCaseImpl(movieRepository, dispatcher)

}