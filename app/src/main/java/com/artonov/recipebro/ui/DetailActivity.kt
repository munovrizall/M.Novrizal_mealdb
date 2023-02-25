package com.artonov.recipebro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.artonov.recipebro.R
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.databinding.ActivityDetailBinding
import com.artonov.recipebro.model.MealsItem
import com.artonov.recipebro.model.RecipeDetail
import com.artonov.recipebro.model.ResponseRecipe
import com.artonov.recipebro.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var recipeDetail: RecipeDetail

    companion object {
        const val EXTRA_RECIPE = "recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getParcelableExtra<MealsItem>(EXTRA_RECIPE)
        detailViewModel.fetchRecipeDetail(recipe?.idMeal!!)

//        detailViewModel.fetchGameDetail(recipe?.id)

        detailViewModel.recipeDetail.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> handleUi(
                    layoutWrapper = false,
                    progressbar = true,
                    errorTv = false,
                )
                is NetworkResult.Error -> {
                    handleUi(
                        layoutWrapper = false,
                        progressbar = false,
                        errorTv = true,
                    )
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    binding.recipeDetail = result.data
                    recipeDetail = result.data!!
                    handleUi(
                        layoutWrapper = true,
                        progressbar = false,
                        errorTv = false,
                    )
                }
            }
        }


//        isFavoriteGame(game)

    }

    private fun handleUi(
        layoutWrapper: Boolean,
        progressbar: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            recipeDetailWrapper.isVisible = layoutWrapper
            progressBar.isVisible = progressbar
            errorText.isVisible = errorTv
        }

    }
}