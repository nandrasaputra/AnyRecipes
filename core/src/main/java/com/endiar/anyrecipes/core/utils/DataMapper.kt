package com.endiar.anyrecipes.core.utils

import com.endiar.anyrecipes.core.data.source.local.entity.FavoriteRecipeEntity
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipeInformationResponse
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipesByIngredientsResponseItem
import com.endiar.anyrecipes.core.domain.model.*

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
                it.creditText ?: "Anonymous"
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

        val instruction = input.instruction.valueOrEmpty()
        val steps = if (instruction.isNotEmpty()) {
            instruction[0].stepList.map {
                Step(
                    it.number,
                    it.step
                )
            }
        } else {
            listOf()
        }

        val ingredients = input.ingredientList.valueOrEmpty().map {
            Ingredient(
                it.ingredientId,
                it.ingredientImageUrl ?: "",
                it.ingredientName ?: "Not Available",
                it.description ?: "Not Available"
            )
        }

        val nutrition = input.nutrition
        val nutrientList = nutrition?.nutrients?.map {
            Nutrient(
                it.title ?: "Not Available",
                it.amount ?: 0.0,
                it.unit ?: "",
                it.percentOfDailyNeeds ?: 0.0
            )
        }
            ?: listOf()

        return RecipeFull(
            input.likesCount ?: -1,
            input.dishImageUrl ?: "",
            input.readyInMinutes ?: -1,
            input.dishName ?: "",
            input.dishId ?: -1,
            input.creditText ?: "Anonymous",
            input.instructions ?: "",
            steps,
            nutrientList,
            ingredients,
            input.glutenFree ?: false,
            input.dairyFree ?: false,
            input.vegetarian ?: false
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