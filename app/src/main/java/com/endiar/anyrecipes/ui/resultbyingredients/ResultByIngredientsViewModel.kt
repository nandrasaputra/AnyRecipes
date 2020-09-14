package com.endiar.anyrecipes.ui.resultbyingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase

class ResultByIngredientsViewModel (
    private val remoteUseCase: RemoteUseCase
): ViewModel() {

    private var currentIngredients = ""
    private var currentSource: LiveData<Resource<List<RecipeByIngredients>>>? = null
    val resultMediatorLiveData = MediatorLiveData<Resource<List<RecipeByIngredients>>>()

    fun getRecipeByIngredients(ingredients: String) {

        if (currentIngredients != ingredients) {
            currentSource?.run {
                resultMediatorLiveData.removeSource(this)
            }

            currentSource = remoteUseCase.getRecipeByIngredientsList(ingredients).asLiveData()

            currentSource?.run {
                resultMediatorLiveData.addSource(this) {
                    if (it is Resource.Success) {
                        currentIngredients = ingredients
                    }
                    resultMediatorLiveData.value = it
                }
            }
        }
    }
}