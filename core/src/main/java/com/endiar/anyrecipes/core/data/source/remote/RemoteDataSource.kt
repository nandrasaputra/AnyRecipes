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
                if (response.isSuccessful) {
                    val data = response.body()!!.recipes
                    if (data.isNotEmpty()) {
                        emit(ApiResponse.Success(data))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    emit(ApiResponse.Error("Something is wrong, Network Error Code: ${response.code()}"))
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSingleDetailRecipes(id: Int): Flow<ApiResponse<GetRecipeInformationResponse>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipeInformation(id)
                if (response.isSuccessful) {
                    val data = response.body()!!
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error("Something is wrong, Network Error Code: ${response.code()}"))
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeByIngredients(ingredients: String): Flow<ApiResponse<List<GetRecipesByIngredientsResponseItem>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipesByIngredients(ingredients, 16)
                if (response.isSuccessful) {
                    val data = response.body()!!.toList()
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error("Something is wrong, Network Error Code: ${response.code()}"))
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("Something is wrong, Error: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

}