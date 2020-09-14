package com.endiar.anyrecipes.core.domain.usecase

import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.core.domain.repository.IAnyRepository
import kotlinx.coroutines.flow.Flow

class RemoteInteractor(private val repository: IAnyRepository) : RemoteUseCase {

    override fun getRecipeGistList(): Flow<Resource<List<RecipeGist>>> = repository.getRecipeGistList()

    override fun getRecipeByIngredientsList(ingredients: String): Flow<Resource<List<RecipeByIngredients>>> =
        repository.getRecipeByIngredientsList(ingredients)

    override fun getRecipeFull(recipeId: Int): Flow<Resource<RecipeFull>> =
        repository.getRecipeFull(recipeId)
}