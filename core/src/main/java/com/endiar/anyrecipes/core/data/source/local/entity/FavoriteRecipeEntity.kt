package com.endiar.anyrecipes.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipe")
data class FavoriteRecipeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "recipeName")
    val recipeName: String,

    @ColumnInfo(name = "authorCredit")
    val authorCredit: String,

    @ColumnInfo(name = "recipeImageUrl")
    val recipeImageUrl: String
)