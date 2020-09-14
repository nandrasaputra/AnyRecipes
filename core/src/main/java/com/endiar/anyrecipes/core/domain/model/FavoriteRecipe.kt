package com.endiar.anyrecipes.core.domain.model

data class FavoriteRecipe(
    val dishImageUrl: String,
    val dishName: String,
    val dishId: Int,
    val creditText: String
)