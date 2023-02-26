package com.artonov.recipebro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.artonov.recipebro.data.LocalDataSource
import com.artonov.recipebro.data.Repository
import com.artonov.recipebro.data.database.MealDatabase
import com.artonov.recipebro.data.database.MealEntity

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(local = local)

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
}