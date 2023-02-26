package com.artonov.recipebro.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artonov.recipebro.model.RecipeDetailItem
import com.artonov.recipebro.utils.Constant.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: RecipeDetailItem?
): Parcelable