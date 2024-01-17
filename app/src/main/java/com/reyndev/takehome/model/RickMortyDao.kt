package com.reyndev.takehome.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RickMortyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT EXISTS (SELECT * FROM favorites WHERE id=:id)")
    fun isFavorite(id: Int): Flow<Boolean>
}