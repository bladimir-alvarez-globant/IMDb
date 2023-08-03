package com.bladoae.imdb.databasemanager.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bladoae.imdb.databasemanager.RoomConverters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie")
@TypeConverters(RoomConverters::class)
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: List<Int>? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
) : Parcelable
