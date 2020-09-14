package com.endiar.anyrecipes.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class GetRandomRecipesResponse(
    @SerializedName("recipes")
    val recipes: List<GetRecipeInformationResponse>
)