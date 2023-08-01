package com.bladoae.imdb.databasemanager

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

@ProvidedTypeConverter
class RoomConverters(val moshi: Moshi) {

    private inline fun <reified T> fromJson(value: String?): List<T?>? {
        if (value == null) return null
        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        val jsonAdapter: JsonAdapter<List<T?>> = moshi.adapter(listMyData)
        return jsonAdapter.fromJson(value)
    }

    private inline fun <reified T> toJson(value: List<T?>?): String {
        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        val jsonAdapter: JsonAdapter<List<T?>?> = moshi.adapter(listMyData)
        return jsonAdapter.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int?>? = fromJson(value)

    @TypeConverter
    fun fromIntList(value: List<Int?>?): String = toJson(value)

}