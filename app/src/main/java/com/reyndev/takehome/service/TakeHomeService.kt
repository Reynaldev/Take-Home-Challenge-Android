package com.reyndev.takehome.service

import com.reyndev.takehome.model.RickMorty
import com.reyndev.takehome.model.RickMortyApiModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://rickandmortyapi.com/api/"

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RickMortyApi {
    @GET("character")
    suspend fun getAllCharacter(): RickMortyApiModel

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id")id: Int): RickMorty
}

class TakeHomeService {
    companion object {
        val takeHomeService: RickMortyApi by lazy {
            retrofit.create(RickMortyApi::class.java)
        }
    }
}