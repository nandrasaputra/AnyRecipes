package com.endiar.anyrecipes.utils

import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.model.FridgeIngredientItem

object FakeData {
    val fakeRecipeGistData = listOf(
        RecipeGist(100,
            "https://spoonacular.com/recipeImages/729366-556x370.jpg",
            120,
            "Plantain Salad",
            729366,
            "Pak Datuk"
        ),
        RecipeGist(120,
            "https://spoonacular.com/recipeImages/640720-556x370.jpg",
            60,
            "Creamy Vegan Butternut Squash Mac and Cheese",
            640720,
            "RM Sederhana"
        ),
        RecipeGist(2,
            "https://spoonacular.com/recipeImages/646034-556x370.jpg",
            8,
            "Guinness Braised Corned Beef and Cabbage",
            646034,
            "Kota Buana"
        ),
        RecipeGist(20000,
            "https://spoonacular.com/recipeImages/631738-556x370.jpg",
            10,
            "Chicken with Grape Tomatoes and Mushrooms",
            631738,
            "Rumah Makan Abang Adek"
        ),
        RecipeGist(2000,
            "https://spoonacular.com/recipeImages/715545-556x370.jpg",
            1000,
            "Chicken and Sausage Jambalaya",
            715545,
            "Beringin Edy"
        )
    )
    val fridgeData = listOf(
        FridgeIngredientItem(
            1,
            "beef",
            "Beef",
            R.drawable.ic_ingredient_beef
        ),
        FridgeIngredientItem(
            2,
            "chicken",
            "Chicken",
            R.drawable.ic_ingredient_chicken
        ),
        FridgeIngredientItem(
            3,
            "crab",
            "Crab",
            R.drawable.ic_ingredient_crab
        ),
        FridgeIngredientItem(
            4,
            "egg",
            "Eggs",
            R.drawable.ic_ingredient_eggs
        ),
        FridgeIngredientItem(
            5,
            "fish",
            "Fish",
            R.drawable.ic_ingredient_fish
        ),
        FridgeIngredientItem(
            6,
            "lobster",
            "Lobster",
            R.drawable.ic_ingredient_lobster
        ),
        FridgeIngredientItem(
            7,
            "octopus",
            "Octopus",
            R.drawable.ic_ingredient_octopus
        ),
        FridgeIngredientItem(
            8,
            "pasta",
            "Pasta",
            R.drawable.ic_ingredient_pasta
        ),
        FridgeIngredientItem(
            9,
            "sausage",
            "Sausage",
            R.drawable.ic_ingredient_sausage
        ),
        FridgeIngredientItem(
            10,
            "shrimp",
            "Shrimp",
            R.drawable.ic_ingredient_shrimp
        ),
        FridgeIngredientItem(
            11,
            "squid",
            "Squid",
            R.drawable.ic_ingredient_squid
        ),
        FridgeIngredientItem(
            12,
            "tofu",
            "Tofu",
            R.drawable.ic_ingredient_tofu
        )
    )
    val fakeRecipeByIngredient = listOf(
        RecipeByIngredients(
            "https://spoonacular.com/recipeImages/729366-556x370.jpg",
            "Plantain Salad",
            729366
        ),
        RecipeByIngredients(
            "https://spoonacular.com/recipeImages/640720-556x370.jpg",
            "Plantain Salad",
            640720
        ),
        RecipeByIngredients(
            "https://spoonacular.com/recipeImages/646034-556x370.jpg",
            "Guinness Braised Corned Beef and Cabbage",
            646034
        ),
        RecipeByIngredients(
            "https://spoonacular.com/recipeImages/631738-556x370.jpg",
            "Chicken with Grape Tomatoes and Mushrooms",
            631738
        ),
        RecipeByIngredients(
            "https://spoonacular.com/recipeImages/715545-556x370.jpg",
            "Chicken and Sausage Jambalaya",
            729366
        )
    )
}