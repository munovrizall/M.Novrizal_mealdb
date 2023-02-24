package com.artonov.recipebro.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseRecipe(

	@field:SerializedName("meals")
	val meals: List<MealsItem?>? = null
) : Parcelable

@Parcelize
data class MealsItem(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val strMeal: String? = null
) : Parcelable
