package com.reyndev.takehome.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.reyndev.takehome.model.Favorite
import com.reyndev.takehome.model.RickMortyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel(private val dao: RickMortyDao) : ViewModel() {
    val favorites: LiveData<List<Favorite>> = dao.getFavorites().asLiveData()

    fun addFavorite(model: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insert(model)

                Log.v(TAG, "${model.name} added to favorite")
            } catch (e: Exception) {
                Log.v(TAG, "Failed to add ${model.name} into favorites")
                e.printStackTrace()
            }
        }
    }

    fun removeFavorite(model: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.delete(model)

                Log.v(TAG, "${model.name} removed from favorite")
            } catch (e: Exception) {
                Log.v(TAG, "Failed to remove ${model.name} from favorites")
                e.printStackTrace()
            }
        }
    }

    fun isFavorite(id: Int) = dao.isFavorite(id).asLiveData()
}

class FavoriteViewModelFactory(private val dao: RickMortyDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}

