package com.reyndev.takehome.model

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