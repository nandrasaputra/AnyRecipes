package com.endiar.anyrecipes.core.data

import com.endiar.anyrecipes.core.data.source.local.LocalDataSource
import com.endiar.anyrecipes.core.data.source.remote.RemoteDataSource
import com.endiar.anyrecipes.core.data.source.remote.network.ApiResponse
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.core.domain.repository.IAnyRepository
import com.endiar.anyrecipes.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AnyRecipeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAnyRepository {

    override fun getRecipeGistList(): Flow<Resource<List<RecipeGist>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getRandomRecipes().first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapGetRecipeInformationResponseToRecipeGist(data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success<List<RecipeGist>>(listOf()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<List<RecipeGist>>(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipeByIngredientsList(ingredients: String): Flow<Resource<List<RecipeByIngredients>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getRecipeByIngredients(ingredients).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapGetRecipesByIngredientsResponseItemToRecipeByIngredients(data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success<List<RecipeByIngredients>>(listOf()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<List<RecipeByIngredients>>(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipeFull(id: Int): Flow<Resource<RecipeFull>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getSingleDetailRecipes(id).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.changeGetRecipeInformationResponseToRecipeFull(data)))
            }
            is ApiResponse.Empty -> {
                // Never Expected To Reach This
                emit(Resource.Error<RecipeFull>("Something is wrong"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<RecipeFull>(apiResponse.errorMessage))
            }
        }
    }

    override suspend fun setFavoriteRecipe(recipe: RecipeFull) {
        localDataSource.setFavoriteRecipe(DataMapper.changeRecipeFullToFavoriteRecipeEntity(recipe))
    }

    override suspend fun removeFavoriteRecipe(recipeId: Int) {
        localDataSource.removeFavoriteRecipe(recipeId)
    }

    override fun getFavoriteRecipeList(): Flow<List<FavoriteRecipe>> {
        return localDataSource.getAllFavoriteRecipe().map {
            DataMapper.mapFavoriteRecipeEntityToFavoriteRecipe(it)
        }
    }

    override fun checkRecipeOnFavorite(id: Int): Flow<Boolean> = flow {
        val data = localDataSource.getSingleFavoriteRecipe(id).first()
        if (data != null) {
            emit(true)
        } else {
            emit(false)
        }
    }
}