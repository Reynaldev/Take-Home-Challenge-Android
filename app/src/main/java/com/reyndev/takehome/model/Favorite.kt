package com.reyndev.takehome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = false)  val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String
)
