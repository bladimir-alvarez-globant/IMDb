package com.bladoae.imdb.data.di

import com.bladoae.imdb.data.repository.MovieRepositoryImpl
import com.bladoae.imdb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppAbstractModule {

    @Binds
    @Singleton
    abstract fun providePokeDexRepository(
        movieRepository: MovieRepositoryImpl
    ): MovieRepository

}