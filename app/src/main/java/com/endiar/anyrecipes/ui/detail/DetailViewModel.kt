package com.endiar.anyrecipes.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.usecase.LocalUseCase
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase

class DetailViewModel(
    private val localUseCase: LocalUseCase,
    private val remoteUseCase: RemoteUseCase
) : ViewModel() {

    private var currentRecipeId = -1
    private var currentRemoteSource: LiveData<Resource<RecipeFull>>? = null
    private var currentLocalSource: LiveData<Boolean>? = null
    private var currentFavoriteStatusIsFavorite = false
    private var currentRecipeData: RecipeFull? = null
    private var isFavoriteFinishLoading = false

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
        currentRecipeData?.let {
            if (!isFavoriteFinishLoading) {
                return
            }
            if (currentFavoriteStatusIsFavorite) {
                localUseCase.removeFavoriteRecipeGist(it.dishId)
            } else {
                localUseCase.setFavoriteRecipeGist(it)
            }
            updateFavoriteStatus()
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
        isFavoriteFinishLoading = true
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