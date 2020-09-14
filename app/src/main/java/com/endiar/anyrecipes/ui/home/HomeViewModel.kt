package com.endiar.anyrecipes.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase

class HomeViewModel(private val remoteUseCase: RemoteUseCase) : ViewModel() {

    private var currentSource : LiveData<Resource<List<RecipeGist>>> =
        remoteUseCase.getRecipeGistList().asLiveData()

    val homeMediatorLiveData = MediatorLiveData<Resource<List<RecipeGist>>>()

    init {
        homeMediatorLiveData.addSource(currentSource) {
            homeMediatorLiveData.postValue(it)
        }
    }

    fun getFreshData() {
        if (homeMediatorLiveData.value !is Resource.Loading) {
            homeMediatorLiveData.removeSource(currentSource)
            currentSource = remoteUseCase.getRecipeGistList().asLiveData()
            homeMediatorLiveData.addSource(currentSource) {
                homeMediatorLiveData.postValue(it)
            }
        }
    }

}