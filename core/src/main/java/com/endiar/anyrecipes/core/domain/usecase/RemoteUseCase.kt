package com.endiar.anyrecipes.core.domain.usecase

import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import kotlinx.coroutines.flow.Flow

interface RemoteUseCase {

    fun getRecipeGistList(): Flow<Resource<List<RecipeGist>>>

    fun getRecipeByIngredientsList(ingredients: String): Flow<Resource<List<RecipeByIngredients>>>

    fun getRecipeFull(recipeId: Int): Flow<Resource<RecipeFull>>

}