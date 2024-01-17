package com.reyndev.takehome.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([Favorite::class], version = 1, exportSchema = false)
abstract class RickMortyDatabase : RoomDatabase() {
    abstract fun rickMortyDao(): RickMortyDao

    companion object {
        @Volatile
        private var INSTANCE: RickMortyDatabase? = null

        fun getDatabase(ctx: Context): RickMortyDatabase =
            INSTANCE ?: synchronized(this) {
                return INSTANCE.let {
                    Room.databaseBuilder(
                        ctx,
                        RickMortyDatabase::class.java,
                        "rickmorty_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
    }
}