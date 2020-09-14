package com.endiar.anyrecipes.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class GetRecipeInformationResponse(
    @SerializedName("aggregateLikes")
    val likesCount: Int?,
    @SerializedName("analyzedInstructions")
    val instruction: List<Instruction>,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("extendedIngredients")
    val ingredientList: List<Ingredient>,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("healthScore")
    val healthScore: Double,
    @SerializedName("id")
    val dishId: Int?,
    @SerializedName("image")
    val dishImageUrl: String?,
    @SerializedName("instructions")
    val instructions: String?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Double,
    @SerializedName("summary")
    val dishSummary: String,
    @SerializedName("title")
    val dishName: String?,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("creditsText")
    val creditText: String?
)

data class Ingredient(
    @SerializedName("id")
    val ingredientId: Int,
    @SerializedName("image")
    val ingredientImageUrl: String,
    @SerializedName("name")
    val ingredientName: String,
    @SerializedName("original")
    val description: String
)

data class Instruction(
    @SerializedName("steps")
    val stepList: List<Step>
)

data class Step(
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
)