package com.artonov.recipebro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.artonov.recipebro.data.LocalDataSource
import com.artonov.recipebro.data.RemoteDataSource
import com.artonov.recipebro.data.Repository
import com.artonov.recipebro.data.network.Service
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.model.RecipeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {

    // API
    private val recipeService = Service.RecipeService
    private val remote = RemoteDataSource(recipeService)

    // LOCAL
//    private val gameDao = GameDatabase.getDatabase(application).gameDao()
//    private val local = LocalDataSource(gameDao)

    private val repository = Repository(remote,null)

    private var _recipeDetail: MutableLiveData<NetworkResult<RecipeDetail>> = MutableLiveData()
    val recipeDetail: LiveData<NetworkResult<RecipeDetail>> = _recipeDetail

    fun fetchRecipeDetail(idMeal: String) {
        viewModelScope.launch {
            repository.remote!!.getRecipeDetailById(idMeal).collect { result ->
                _recipeDetail.value = result
            }
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