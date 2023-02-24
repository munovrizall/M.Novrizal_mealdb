package com.artonov.recipebro.data.network

import com.artonov.recipebro.data.network.api.RecipeApi
import com.artonov.recipebro.utils.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val RecipeService: RecipeApi by lazy {
        retrofit.create(RecipeApi::class.java)
    }
}