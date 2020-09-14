package com.endiar.anyrecipes.favoritefeature.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.usecase.LocalUseCase

class FavoriteViewModel(private val localUseCase: LocalUseCase) : ViewModel() {

    private var currentSource : LiveData<List<FavoriteRecipe>> =
        localUseCase.getFavoriteRecipeGistList().asLiveData()
    val favoriteMediatorLiveData = MediatorLiveData<List<FavoriteRecipe>>()

    init {
        favoriteMediatorLiveData.addSource(currentSource) {
            favoriteMediatorLiveData.value = it
        }
    }

    fun removeRecipeFromFavorite(recipeId: Int) {
        localUseCase.removeFavoriteRecipeGist(recipeId)
    }
}