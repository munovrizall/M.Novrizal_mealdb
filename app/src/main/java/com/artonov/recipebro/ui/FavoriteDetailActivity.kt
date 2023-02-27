package com.artonov.recipebro.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.artonov.recipebro.R
import com.artonov.recipebro.data.database.MealEntity
import com.artonov.recipebro.databinding.ActivityFavoriteDetailBinding
import com.artonov.recipebro.viewmodel.FavoriteDetailViewModel
import com.bumptech.glide.Glide

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private val favoriteDetailViewModel by viewModels<FavoriteDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)

        binding.apply {
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutExpandedTextStyle)
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CustomAppBarTextStyle)
            collapsingToolbar.title = favoriteMeal?.meal?.strMeal
        }


        binding.apply {
            Glide.with(this@FavoriteDetailActivity)
                .load(favoriteMeal?.meal?.strMealThumb)
                .error(R.drawable.ic_launcher_background)
                .into(ivFavoriteDetail)
            tvFavoriteDetailTitle.text = favoriteMeal?.meal?.strMeal
            tvFavoriteDetailInstruction.text = favoriteMeal?.meal?.strInstructions
            tvArea.text = favoriteMeal?.meal?.strArea
            tvMealId.text = favoriteMeal?.meal?.idMeal
        }

        val link = favoriteMeal?.meal?.strSource.toString()

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

        favoriteDetailViewModel.fetchListMeal()

        binding.btnFavoriteDetail.setOnClickListener {
            deleteFavoriteMeal(favoriteMeal)
            val delete = Intent(this, FavoriteActivity::class.java)
            startActivity(delete)
            finish()
        }
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

    private fun deleteFavoriteMeal(mealEntity: MealEntity?) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun openRecipe(recipeLink: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(recipeLink)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }
}