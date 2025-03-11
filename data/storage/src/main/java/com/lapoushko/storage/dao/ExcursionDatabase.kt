package com.lapoushko.storage.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lapoushko.storage.entity.ExcursionDb
import com.lapoushko.storage.util.ConstantsDatabase
import com.lapoushko.storage.util.CustomTypeConverters

/**
 * @author Lapoushko
 */
@Database(
    entities = [
        ExcursionDb::class
    ],
    version = ConstantsDatabase.EXCURSION_TABLE_VERSION
)
@TypeConverters(CustomTypeConverters::class)
abstract class ExcursionDatabase: RoomDatabase() {
    abstract fun ExcursionDao(): ExcursionDao
}