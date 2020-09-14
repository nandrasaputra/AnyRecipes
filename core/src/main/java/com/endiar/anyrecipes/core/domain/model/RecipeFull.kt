package com.endiar.anyrecipes.core.domain.model

data class RecipeFull(
    val likesCount: Int,
    val dishImageUrl: String,
    val cookingTime: Int,
    val dishName: String,
    val dishId: Int,
    val creditText: String,
    val shortInstruction: String
)