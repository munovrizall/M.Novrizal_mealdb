package com.artonov.recipebro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.artonov.recipebro.data.LocalDataSource
import com.artonov.recipebro.data.RemoteDataSource
import com.artonov.recipebro.data.Repository
import com.artonov.recipebro.data.database.MealDatabase
import com.artonov.recipebro.data.database.MealEntity
import com.artonov.recipebro.data.network.Service
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.model.ResponseRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(application: Application): AndroidViewModel(application) {
    // Api
    private val remoteService = Service.RecipeService
    private val remote = RemoteDataSource(remoteService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote,local)

    private var _recommendationList: MutableLiveData<NetworkResult<ResponseRecipe>> = MutableLiveData()
    val recommendationList: LiveData<NetworkResult<ResponseRecipe>> = _recommendationList


    fun fetchListMeal() {
        viewModelScope.launch {
            repository.remote!!.getRecipe().collect { res ->
                _recommendationList.value = res
            }
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity?){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}