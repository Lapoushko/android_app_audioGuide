package com.lapoushko.storage.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lapoushko.storage.entity.Point

/**
 * @author Lapoushko
 */
//class CustomTypeConverters {
//    @TypeConverter
//    fun convertListPointToString(list: List<Point>?): String? {
//        return list?.joinToString(separator = SEPARATOR) {
//            "${it.name}$SEPARATOR${it.text}$SEPARATOR${it.audio}$SEPARATOR${it.image}$SEPARATOR${it.point?.first}$SEPARATOR${it.point?.second}$SEPARATOR"
//        }
//    }
//
//    @TypeConverter
//    fun convertStringToListPoint(string: String?) : List<Point>?{
//        val data = string?.split(SEPARATOR)
//
//    }
//
//    companion object {
//        private const val SEPARATOR = ", "
//    }
//}
class CustomTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromPointList(value: List<Point>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPointList(value: String?): List<Point>? {
        return gson.fromJson(value, object : TypeToken<List<Point>>() {}.type)
    }
}
