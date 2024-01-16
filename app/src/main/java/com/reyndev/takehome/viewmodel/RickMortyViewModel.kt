package com.reyndev.takehome.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reyndev.takehome.model.RickMorty
import com.reyndev.takehome.service.TakeHomeService
import kotlinx.coroutines.launch

private const val TAG = "RickMortyViewModel"

class RickMortyViewModel : ViewModel() {
    private val _characters = MutableLiveData<List<RickMorty>>()
    val characters: LiveData<List<RickMorty>> = _characters

    private val _character = MutableLiveData<RickMorty>()
    val character: LiveData<RickMorty> = _character

    init {
        getCharactersList()
    }

    fun getCharactersList() {
        viewModelScope.launch {
            try {
                _characters.value = TakeHomeService.takeHomeService.getAllCharacter().results

                Log.v(TAG, "Successfully retrieve all characters data")
            } catch (e: Exception) {
                Log.wtf(TAG, "Failed to retrieve characters data\n${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            try {
                _character.value = TakeHomeService.takeHomeService.getCharacterById(id)

                Log.v(TAG, "Successfully retrieve character id of ${id}")
            } catch (e: Exception) {
                Log.wtf(TAG, "Failed to retrieve character of id ${id}\n${e.message}")
                e.printStackTrace()
            }
        }
    }
}

class RickMortyViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RickMortyViewModel::class.java)) {
            return RickMortyViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}