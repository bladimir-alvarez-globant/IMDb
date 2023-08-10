package com.bladoae.imdb.databasemanager.di

import android.content.Context
import androidx.room.Room
import com.bladoae.imdb.databasemanager.ImdbDatabase
import com.bladoae.imdb.databasemanager.RoomConverters
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): ImdbDatabase {
        return Room.databaseBuilder(
            context,
            ImdbDatabase::class.java,
            ImdbDatabase.DATABASE_NAME
        )
            .addTypeConverter(RoomConverters(moshi))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDataDictionaryDao(pavenDatabase: ImdbDatabase): MovieDao =
        pavenDatabase.movieDao()

}