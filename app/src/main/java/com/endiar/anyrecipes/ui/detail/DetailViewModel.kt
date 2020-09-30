package com.endiar.anyrecipes.ui.detail

import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.usecase.LocalUseCase
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val localUseCase: LocalUseCase,
    private val remoteUseCase: RemoteUseCase
) : ViewModel() {

    private var currentRecipeId = -1
    private var currentRemoteSource: LiveData<Resource<RecipeFull>>? = null
    private var currentLocalSource: LiveData<Boolean>? = null
    private var currentFavoriteStatusIsFavorite = false
    private var currentRecipeData: RecipeFull? = null

    val detailRemoteDataMediatorLiveData = MediatorLiveData<Resource<RecipeFull>>()
    val detailLocalDataMediatorLiveData = MediatorLiveData<Boolean>()

    fun getDetailData(recipeId: Int) {
        if (recipeId != currentRecipeId) {
            currentRemoteSource?.run {
                detailRemoteDataMediatorLiveData.removeSource(this)
            }
            currentLocalSource?.run {
                detailLocalDataMediatorLiveData.removeSource(this)
            }
            if (recipeId == -1) {
                detailRemoteDataMediatorLiveData.value = Resource.Error("Error: Cannot Find Such Recipe")
                currentRecipeId = recipeId
            } else {
                getRecipeById(recipeId)
                getFavoriteStatusById(recipeId)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            currentRecipeData?.let {
                if (currentFavoriteStatusIsFavorite) {
                    localUseCase.removeFavoriteRecipeGist(it.dishId)
                } else {
                    localUseCase.setFavoriteRecipeGist(it)
                }
                updateFavoriteStatus()
            }
        }
    }

    private fun getRecipeById(recipeId: Int) {
        currentRemoteSource = remoteUseCase.getRecipeFull(recipeId).asLiveData()
        currentRemoteSource?.run {
            detailRemoteDataMediatorLiveData.addSource(this) {
                if (it is Resource.Success) {
                    currentRecipeId = recipeId
                    currentRecipeData = it.data
                }
                detailRemoteDataMediatorLiveData.value = it
            }
        }
    }

    private fun getFavoriteStatusById(recipeId: Int) {
        currentLocalSource = localUseCase.checkRecipeOnFavorite(recipeId).asLiveData()
        currentLocalSource?.run {
            detailLocalDataMediatorLiveData.addSource(this) {
                currentFavoriteStatusIsFavorite = it
                detailLocalDataMediatorLiveData.value = it
            }
        }
    }

    private fun updateFavoriteStatus() {
        currentLocalSource?.run {
            detailLocalDataMediatorLiveData.removeSource(this)
        }
        currentRecipeData?.let {
            currentLocalSource = localUseCase.checkRecipeOnFavorite(it.dishId).asLiveData()
        }
        currentLocalSource?.run {
            detailLocalDataMediatorLiveData.addSource(this) {
                detailLocalDataMediatorLiveData.value = it
                currentFavoriteStatusIsFavorite = it
            }
        }
    }
}