package com.reyndev.takehome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RickMortyApiModel(
    val results: List<RickMorty>
)

data class RickMorty(
    val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String
)

data class Origin(
    val url: String,
    val name: String
)

data class Location(
    val url: String,
    val name: String
)

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = false)  val id: Int,
    val name: String,
    val image: String
)