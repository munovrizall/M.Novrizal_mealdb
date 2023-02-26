package com.artonov.recipebro.data

import android.util.Log
import com.artonov.recipebro.data.network.api.RecipeApi
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.model.RecipeDetail
import com.artonov.recipebro.model.RecipeDetailItem
import com.artonov.recipebro.model.ResponseRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val recipeApi: RecipeApi) {
    suspend fun getRecipe(): Flow<NetworkResult<ResponseRecipe>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val recipes = recipeApi.getRecipeList()

            // request data successful
            if (recipes.isSuccessful){
                val responseBody = recipes.body()

                // if data empty
                if (responseBody?.meals?.isEmpty() == true){
                    emit(NetworkResult.Error("Recipes List Not Found"))
                }else{
                    emit(NetworkResult.Success(responseBody!!))
                }

            }else{

                // request data failed
                Log.d("apiServiceError", "statusCode:${recipes.code()}, message:${recipes.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }

        }catch (err:Exception){
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getRecipeDetailById(idMeal: Int): Flow<NetworkResult<RecipeDetail>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val recipeDetailItem = recipeApi.getRecipeDetailById(idMeal)
            if (recipeDetailItem.isSuccessful) {
                val responseBody = recipeDetailItem.body()

                if (responseBody != null) {
                    Log.d(
                        "apiServiceError",
                        "Success statusCode:${recipeDetailItem.code()}, message:${recipeDetailItem.message()}"
                    )
                    emit(NetworkResult.Success(responseBody))
                } else {
                    emit(NetworkResult.Error("Can't fetch detail recipe."))
                }
            } else {
                // request data failed
                Log.d(
                    "apiServiceError",
                    "statusCode:${recipeDetailItem.code()}, message:${recipeDetailItem.message()}"
                )
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}