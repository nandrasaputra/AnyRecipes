package com.endiar.anyrecipes.favoritefeature.ui

import androidx.lifecycle.*
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.usecase.LocalUseCase
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            localUseCase.removeFavoriteRecipeGist(recipeId)
        }
    }
}