package com.endiar.anyrecipes.domain

import com.endiar.anyrecipes.core.data.AnyRecipeRepository
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.core.domain.usecase.RemoteInteractor
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DomainUnitTest {

    @Mock
    private lateinit var anyRecipeRepository: AnyRecipeRepository
    private lateinit var remoteUseCase: RemoteUseCase
    private val fakeRecipeGistList = listOf(
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
    private val fakeRecipeByIngredientList = listOf(
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
    private val fakeDetailData = RecipeFull(
        100,
        "https://spoonacular.com/recipeImages/729366-556x370.jpg",
        60,
        "Plantain Salad",
        729366,
        "",
        "",
        listOf(),
        listOf(),
        listOf(),
        isGlutenFree = false,
        isDairyFree = false,
        isVegetarian = false
    )
    private val detailRecipeId = 729366
    private val ingredients = "chicken, tofu"

    @Before
    fun before() {
        remoteUseCase = RemoteInteractor(anyRecipeRepository)

        `when`(anyRecipeRepository.getRecipeGistList()).thenReturn(flow {
            Resource.Success(fakeRecipeGistList)
        })

        `when`(anyRecipeRepository.getRecipeByIngredientsList(ingredients)).thenReturn(flow {
            Resource.Success(fakeRecipeByIngredientList)
        })

        `when`(anyRecipeRepository.getRecipeFull(detailRecipeId)).thenReturn(flow {
            fakeDetailData
        })
    }

    @Test
    fun `test getRecipeGistList`() {
        remoteUseCase.getRecipeGistList()

        verify(anyRecipeRepository).getRecipeGistList()
        verifyNoMoreInteractions(anyRecipeRepository)
    }

    @Test
    fun `test getRecipeByIngredientsList`() {
        remoteUseCase.getRecipeByIngredientsList(ingredients)

        verify(anyRecipeRepository).getRecipeByIngredientsList(ingredients)
        verifyNoMoreInteractions(anyRecipeRepository)
    }

    @Test
    fun `test getRecipeFull`() {
        remoteUseCase.getRecipeFull(detailRecipeId)

        verify(anyRecipeRepository).getRecipeFull(detailRecipeId)
        verifyNoMoreInteractions(anyRecipeRepository)
    }

}