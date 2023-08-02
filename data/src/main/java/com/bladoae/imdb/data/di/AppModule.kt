package com.bladoae.imdb.data.di

import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.apiservice.MovieApiServiceImpl
import com.bladoae.imdb.data.repository.MovieRepositoryImpl
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.domain.repository.MovieRepository
import com.bladoae.imdb.requestmanager.ApiService
import com.bladoae.imdb.requestmanager.manager.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(
        @Named("baseBackendUrl") baseBackendUrl: String
    ): ApiService {
        return ServiceGenerator(baseBackendUrl).createService()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(
        service: ApiService
    ): MovieApiService {
        return MovieApiServiceImpl(service)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        movieDao: MovieDao,
        dispatcher: CoroutineContext
    ) : MovieRepository {
        return MovieRepositoryImpl(movieApiService, movieDao, dispatcher)
    }

}