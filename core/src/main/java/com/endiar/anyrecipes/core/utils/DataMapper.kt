package com.endiar.anyrecipes.core.utils

import com.endiar.anyrecipes.core.data.source.local.entity.FavoriteRecipeEntity
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipeInformationResponse
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipesByIngredientsResponseItem
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist

object DataMapper {
    fun mapGetRecipeInformationResponseToRecipeGist(
        input: List<GetRecipeInformationResponse>
    ): List<RecipeGist> {
        return input.map {
            RecipeGist(
                it.likesCount ?: 0,
                it.dishImageUrl ?: "",
                it.readyInMinutes ?: -1,
                it.dishName ?: "",
                it.dishId ?: -1,
                it.creditText ?: ""
            )
        }
    }

    fun mapGetRecipesByIngredientsResponseItemToRecipeByIngredients (
        input: List<GetRecipesByIngredientsResponseItem>
    ): List<RecipeByIngredients> {
        return input.map {
            RecipeByIngredients(
                it.imageUrl ?: "",
                it.title ?: "",
                it.id ?: -1
            )
        }
    }

    fun changeGetRecipeInformationResponseToRecipeFull(
        input: GetRecipeInformationResponse
    ) : RecipeFull {
        return RecipeFull(
            input.likesCount ?: -1,
            input.dishImageUrl ?: "",
            input.readyInMinutes ?: -1,
            input.dishName ?: "",
            input.dishId ?: -1,
            input.creditText ?: "",
            input.instructions ?: ""
        )
    }

    fun changeRecipeFullToFavoriteRecipeEntity(
        input: RecipeFull
    ): FavoriteRecipeEntity {
        return FavoriteRecipeEntity(
            input.dishId,
            input.dishName,
            input.creditText,
            input.dishImageUrl
        )
    }

    fun mapFavoriteRecipeEntityToFavoriteRecipe(
        input: List<FavoriteRecipeEntity>
    ): List<FavoriteRecipe> {
        return input.map {
            FavoriteRecipe(
                it.recipeImageUrl,
                it.recipeName,
                it.recipeId,
                it.authorCredit
            )
        }
    }

}