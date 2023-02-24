package com.artonov.recipebro.data.network.api

import com.artonov.recipebro.model.ResponseRecipe
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {
    @GET("api/json/v1/1/filter.php?c=Seafood")
    suspend fun getRecipeList() : Response<ResponseRecipe>
}