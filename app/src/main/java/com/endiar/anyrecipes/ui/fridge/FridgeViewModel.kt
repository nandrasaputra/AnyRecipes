package com.endiar.anyrecipes.ui.fridge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.endiar.anyrecipes.model.FridgeIngredientItemState

class FridgeViewModel : ViewModel() {

    private val _fridgeIngredientItemStateMap = MutableLiveData<MutableMap<Int, FridgeIngredientItemState>>(mutableMapOf())
    val fridgeIngredientItemStateMap: LiveData<MutableMap<Int, FridgeIngredientItemState>> = _fridgeIngredientItemStateMap

    fun updateFridgeIngredientItemStateMap(newMap: MutableMap<Int, FridgeIngredientItemState>) {
        _fridgeIngredientItemStateMap.postValue(newMap)
    }
}