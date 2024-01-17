package com.reyndev.takehome

import android.app.Application
import com.reyndev.takehome.model.RickMortyDatabase

class TakeHomeApplication : Application() {
    val database: RickMortyDatabase by lazy { RickMortyDatabase.getDatabase(this) }
}