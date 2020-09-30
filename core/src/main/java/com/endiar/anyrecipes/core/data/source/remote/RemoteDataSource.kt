package com.endiar.anyrecipes.core.data.source.remote

import com.endiar.anyrecipes.core.data.source.remote.network.ApiResponse
import com.endiar.anyrecipes.core.data.source.remote.network.SpoonacularApiService
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipeInformationResponse
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipesByIngredientsResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val spoonacularApiService: SpoonacularApiService){

    suspend fun getRandomRecipes(): Flow<ApiResponse<List<GetRecipeInformationResponse>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRandomRecipes(15)
                val recipes = response.recipes
                if (recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSingleDetailRecipes(id: Int): Flow<ApiResponse<GetRecipeInformationResponse>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipeInformation(id, true)
                emit(ApiResponse.Success(response))
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeByIngredients(ingredients: String): Flow<ApiResponse<List<GetRecipesByIngredientsResponseItem>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipesByIngredients(ingredients, 16)
                val recipe = response.toList()
                if (recipe.isNotEmpty()) {
                    emit(ApiResponse.Success(recipe))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

}