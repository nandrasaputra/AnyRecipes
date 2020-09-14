package com.endiar.anyrecipes.model

data class FridgeIngredientItemState (
    val menuId: Int,
    val menuValue: String,
    val isChecked: Boolean = false
)