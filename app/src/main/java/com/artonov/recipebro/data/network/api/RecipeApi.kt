package com.artonov.recipebro.data.network.api

import com.artonov.recipebro.model.MealsItem
import com.artonov.recipebro.model.RecipeDetail
import com.artonov.recipebro.model.RecipeDetailItem
import com.artonov.recipebro.model.ResponseRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("api/json/v1/1/filter.php?c=Seafood")
    suspend fun getRecipeList() : Response<ResponseRecipe>

//    @GET("api/json/v1/1/lookup.php?i={idMeal}")
//    suspend fun getRecipeDetailById(
//        @Path("idMeal") idMeal:String
//    ):Response<RecipeDetail>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecipeDetailById(@Query("i") i: Int
    ): Response<RecipeDetail>
}