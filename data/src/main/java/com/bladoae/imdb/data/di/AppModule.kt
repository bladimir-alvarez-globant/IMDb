package com.bladoae.imdb.data.di

import com.bladoae.imdb.data.apiservice.MovieApiService
import com.bladoae.imdb.data.apiservice.MovieApiServiceImpl
import com.bladoae.imdb.data.repository.MovieRepositoryImpl
import com.bladoae.imdb.data.repository.UserRepositoryImpl
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.domain.repository.MovieRepository
import com.bladoae.imdb.domain.repository.UserRepository
import com.bladoae.imdb.domain.usermanager.UserAuthentication
import com.bladoae.imdb.requestmanager.ApiService
import com.bladoae.imdb.requestmanager.manager.ServiceGenerator
import com.bladoae.imdb.requestmanager.utils.NetworkConnectivity
import com.squareup.moshi.Moshi
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
        @Named("baseBackendUrl") baseBackendUrl: String,
        networkConnectivity: NetworkConnectivity,
        moshi: Moshi
    ): ApiService {
        return ServiceGenerator(baseBackendUrl, networkConnectivity, moshi).createService()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(
        service: ApiService,
        @Named("apiKey") apiKey: String,
    ): MovieApiService {
        return MovieApiServiceImpl(service, apiKey)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        movieDao: MovieDao,
        dispatcher: CoroutineContext,
        @Named("baseImageUrl") baseImageUrl: String
    ) : MovieRepository {
        return MovieRepositoryImpl(movieApiService, movieDao, dispatcher, baseImageUrl)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userAuthentication: UserAuthentication
    ) : UserRepository {
        return UserRepositoryImpl(userAuthentication)
    }

}