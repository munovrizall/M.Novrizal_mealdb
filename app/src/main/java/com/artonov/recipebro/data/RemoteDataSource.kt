package com.artonov.recipebro.data

import android.util.Log
import com.artonov.recipebro.data.network.api.RecipeApi
import com.artonov.recipebro.data.network.handler.NetworkResult
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
}