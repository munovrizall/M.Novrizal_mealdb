package com.artonov.recipebro.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.artonov.recipebro.data.LocalDataSource
import com.artonov.recipebro.data.RemoteDataSource
import com.artonov.recipebro.data.Repository
import com.artonov.recipebro.data.database.MealDatabase
import com.artonov.recipebro.data.database.MealEntity
import com.artonov.recipebro.data.network.Service
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.model.RecipeDetail
import com.artonov.recipebro.model.RecipeDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {

    // API
    private val recipeService = Service.RecipeService
    private val remote = RemoteDataSource(recipeService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote,local)

    private var _recipeDetail: MutableLiveData<NetworkResult<RecipeDetail>> = MutableLiveData()
    val recipeDetail: LiveData<NetworkResult<RecipeDetail>> = _recipeDetail

    fun fetchRecipeDetail(idMeal: Int) {
        viewModelScope.launch {
            repository.remote!!.getRecipeDetailById(idMeal).collect { result ->
                _recipeDetail.value = result
            }
        }
    }

    val favoriteMealList:LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()

    fun insertFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }

//    val favoriteGameList: LiveData<List<GameEntity>> = repository.local!!.listGame().asLiveData()
//    fun insertFavoriteGame(gameEntity: GameEntity){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local!!.insertGame(gameEntity)
//        }
//    }
//
//    fun deleteFavoriteGame(gameEntity: GameEntity){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local!!.deleteGame(gameEntity)
//        }
//    }


}