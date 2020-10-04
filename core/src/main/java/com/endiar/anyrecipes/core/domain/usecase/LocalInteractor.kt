package com.endiar.anyrecipes.core.domain.usecase

import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.repository.IAnyRepository
import kotlinx.coroutines.flow.Flow

class LocalInteractor(private val repository: IAnyRepository) : LocalUseCase {

    override suspend fun setFavoriteRecipeGist(recipe: RecipeFull) = repository.setFavoriteRecipe(recipe)

    override suspend fun removeFavoriteRecipeGist(recipeId: Int) = repository.removeFavoriteRecipe(recipeId)

    override fun getFavoriteRecipeGistList(): Flow<List<FavoriteRecipe>> = repository.getFavoriteRecipeList()

    override fun checkRecipeOnFavorite(id: Int): Flow<Boolean> = repository.checkRecipeOnFavorite(id)

}