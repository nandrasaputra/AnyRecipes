package com.endiar.anyrecipes.core.domain.usecase

import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import kotlinx.coroutines.flow.Flow

interface LocalUseCase {

    fun setFavoriteRecipeGist(recipe: RecipeFull)

    fun removeFavoriteRecipeGist(recipeId: Int)

    fun getFavoriteRecipeGistList(): Flow<List<FavoriteRecipe>>

    fun checkRecipeOnFavorite(id: Int): Flow<Boolean>

}