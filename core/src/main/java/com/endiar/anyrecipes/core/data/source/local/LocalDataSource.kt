package com.endiar.anyrecipes.core.data.source.local

import com.endiar.anyrecipes.core.data.source.local.entity.FavoriteRecipeEntity
import com.endiar.anyrecipes.core.data.source.local.room.FavoriteRecipeDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteRecipeDao: FavoriteRecipeDao) {

    fun getAllFavoriteRecipe(): Flow<List<FavoriteRecipeEntity>> = favoriteRecipeDao.getAllFavoriteRecipes()

    suspend fun setFavoriteRecipe(favoriteRecipe: FavoriteRecipeEntity) = favoriteRecipeDao.setFavoriteRecipe(favoriteRecipe)

    suspend fun removeFavoriteRecipe(recipeId: Int) = favoriteRecipeDao.removeFavoriteRecipe(recipeId)

    fun getSingleFavoriteRecipe(recipeId: Int): Flow<FavoriteRecipeEntity?> = favoriteRecipeDao.getSingleFavoriteRecipe(recipeId)

}