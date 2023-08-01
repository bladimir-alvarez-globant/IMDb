package com.bladoae.imdb.databasemanager.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bladoae.imdb.databasemanager.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movie")
    fun deleteMovie(): Int

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :name || '%'")
    suspend fun selectMovie(name: String): List<MovieEntity>

}