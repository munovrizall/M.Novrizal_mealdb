package com.artonov.recipebro.ui

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.artonov.recipebro.R
import com.artonov.recipebro.data.database.MealEntity
import com.artonov.recipebro.data.network.handler.NetworkResult
import com.artonov.recipebro.databinding.ActivityDetailBinding
import com.artonov.recipebro.model.MealsItem
import com.artonov.recipebro.model.RecipeDetail
import com.artonov.recipebro.model.RecipeDetailItem
import com.artonov.recipebro.viewmodel.DetailViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var recipeDetail: RecipeDetail
    private var recipeId: RecipeDetailItem? = null

    companion object {
        const val EXTRA_RECIPE = "recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutExpandedTextStyle);
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CustomAppBarTextStyle);
        }

        val recipe = intent.getParcelableExtra<MealsItem>(EXTRA_RECIPE)
        Log.d("test", recipe.toString())
        detailViewModel.fetchRecipeDetail(recipe?.idMeal!!.toInt())

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
                    val res = result.data!!.meals
                    for (recipeDetailItem in res!!) {
                        recipeId = recipeDetailItem
                        Log.d("apiServiceSuccesActivity", recipeDetailItem.toString())
                        binding.recipeDetailItem = recipeDetailItem
                        binding.apply {
                            Glide.with(this@DetailActivity)
                                .load(recipeDetailItem?.strMealThumb)
                                .error(R.drawable.ic_launcher_background)
                                .into(ivDetailRecipe)
                        }
                        val link = recipeDetailItem?.strSource.toString()

                        binding.btnWeb.setOnClickListener() {
                            try {
                                openRecipe(link)
                            } catch (e: Exception) {
                                Toast.makeText(this, "Link not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        binding.btnYoutube.setOnClickListener() {
                            try {
                                openRecipe(link)
                            } catch (e: Exception) {
                                Toast.makeText(this, "Link not found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    Log.d("test", result.data.toString())
                    handleUi(
                        layoutWrapper = true,
                        progressbar = false,
                        errorTv = false,
                    )
                }
            }
        }
        isFavoriteMeal(recipe)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button click here
                onBackPressed()
                overridePendingTransition(0, 0)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun isFavoriteMeal(mealSelected: MealsItem) {
        detailViewModel.favoriteMealList.observe(this@DetailActivity) { res ->
            val meal = res.find { fav ->
                fav.meal!!.idMeal == mealSelected.idMeal
            }
            if (meal != null) {
                binding.btnFavorite.apply {
                    setText("Remove from Favorite")
                    setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.white
                        )
                    )
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.red
                        )
                    )
                    setOnClickListener {
                        deleteFavoriteMeal(meal.id)
                    }
                }
            } else {
                binding.btnFavorite.apply {
                    setText("Add to favorite")
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.yellow
                        )
                    )
                    setOnClickListener {
                        insertFavoriteMeal()
                    }
                }
            }
        }
    }


    private fun deleteFavoriteMeal(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId, recipeId)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteMeal() {
        Log.d("test", recipeId.toString())
        val mealEntity = MealEntity(meal = recipeId)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully added to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun openRecipe(recipeLink: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(recipeLink)
        startActivity(intent)
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