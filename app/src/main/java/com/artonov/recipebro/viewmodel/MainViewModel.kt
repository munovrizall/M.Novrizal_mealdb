package com.artonov.recipebro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artonov.recipebro.data.RemoteDataSource
import com.artonov.recipebro.data.Repository
import com.artonov.recipebro.data.network.Service
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.model.ResponseRecipe
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val remoteService = Service.RecipeService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _recipeList: MutableLiveData<NetworkResult<ResponseRecipe>> = MutableLiveData()
    val recipeList: LiveData<NetworkResult<ResponseRecipe>> = _recipeList

    init {
        fetchRecipeList()
    }

    private fun fetchRecipeList() {
        viewModelScope.launch {
            repository.remote?.getRecipe()?.collect { res ->
                _recipeList.value = res
            }
        }
    }

}