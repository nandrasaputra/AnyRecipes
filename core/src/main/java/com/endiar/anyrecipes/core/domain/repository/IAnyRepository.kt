package com.endiar.anyrecipes.core.domain.repository

import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import kotlinx.coroutines.flow.Flow

interface IAnyRepository {

    // For HomeFragment
    fun getRecipeGistList(): Flow<Resource<List<RecipeGist>>>

    // For ResultByIngredientsFragment
    fun getRecipeByIngredientsList(ingredients: String): Flow<Resource<List<RecipeByIngredients>>>

    // For DetailFragment
    fun getRecipeFull(id: Int): Flow<Resource<RecipeFull>>
    fun setFavoriteRecipe(recipe: RecipeFull)
    fun removeFavoriteRecipe(recipeId: Int)
    fun checkRecipeOnFavorite(id: Int): Flow<Boolean>

    // For FavoriteFragment
    fun getFavoriteRecipeList(): Flow<List<FavoriteRecipe>>

}