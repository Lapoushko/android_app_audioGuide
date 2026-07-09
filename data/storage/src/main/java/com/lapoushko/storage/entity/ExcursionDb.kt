package com.lapoushko.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lapoushko.storage.util.ConstantsDatabase
import com.lapoushko.storage.util.CustomTypeConverters

/**
* @author Lapoushko
*/
@Entity(tableName = ConstantsDatabase.EXCURSION_TABLE_NAME)
data class ExcursionDb(
    @PrimaryKey val numId: Long? = null,
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val distance: Long? = null,
    val categories: List<String>? = null,
    val countRating: Long? = null,
    val age: Int? = null,
    val rating: Double? = null,
    @TypeConverters(CustomTypeConverters::class)
    val points: List<Point>? = null,
)

data class Point(
    val name: String? = null,
    val text: String? = null,
    val image: String? = null,
    val point: Pair<Double, Double>? = null,
    val audio: String? = null
)