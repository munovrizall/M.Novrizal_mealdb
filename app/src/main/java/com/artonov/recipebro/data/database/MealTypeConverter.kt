package com.artonov.recipebro.data.database

import androidx.room.TypeConverter
import com.artonov.recipebro.model.RecipeDetailItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal: RecipeDetailItem?): String {
        return gson.toJson(meal)
    }

    @TypeConverter
    fun mealStringToData(string: String): RecipeDetailItem? {
        val listType = object : TypeToken<RecipeDetailItem?>() {}.type
        return gson.fromJson(string, listType)
    }
}