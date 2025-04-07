package com.lapoushko.storage.di

import android.content.Context
import androidx.room.Room
import com.lapoushko.storage.dao.ExcursionDao
import com.lapoushko.storage.dao.ExcursionDatabase
import com.lapoushko.storage.util.ConstantsDatabase

/**
 * @author Lapoushko
 */
fun provideRoomDatabase(context: Context): ExcursionDatabase {
    return Room.databaseBuilder(context, ExcursionDatabase::class.java, ConstantsDatabase.EXCURSION_DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideDao(database: ExcursionDatabase): ExcursionDao {
    return database.ExcursionDao()
}