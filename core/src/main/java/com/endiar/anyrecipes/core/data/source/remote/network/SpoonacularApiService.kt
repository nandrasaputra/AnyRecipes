package com.endiar.anyrecipes.core.data.source.remote.network

import com.endiar.anyrecipes.core.data.source.remote.response.GetRandomRecipesResponse
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipeInformationResponse
import com.endiar.anyrecipes.core.data.source.remote.response.GetRecipesByIngredientsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {

    @GET("random")
    suspend fun getRandomRecipes(
        @Query("number") resultLimit: Int
    ): Response<GetRandomRecipesResponse>

    @GET("{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int
    ): Response<GetRecipeInformationResponse>

    @GET("findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") resultLimit: Int
    ): Response<GetRecipesByIngredientsResponse>
}