package com.bladoae.imdb.databasemanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bladoae.imdb.databasemanager.daos.MovieDao
import com.bladoae.imdb.databasemanager.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImdbDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "imdb_db"
    }

    abstract fun movieDao(): MovieDao

}