package com.reyndev.takehome.model

import com.squareup.moshi.Json

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
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)