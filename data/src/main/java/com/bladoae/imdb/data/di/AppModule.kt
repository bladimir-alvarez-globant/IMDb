package com.bladoae.imdb.data.di

import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.apiservice.MovieApiServiceImpl
import com.bladoae.imdb.requestmanager.ApiService
import com.bladoae.imdb.requestmanager.manager.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

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

}