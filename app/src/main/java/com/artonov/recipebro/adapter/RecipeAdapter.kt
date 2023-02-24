package com.artonov.recipebro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artonov.recipebro.R
import com.artonov.recipebro.databinding.ItemRowRecipesBinding
import com.artonov.recipebro.model.MealsItem
import com.bumptech.glide.Glide

class RecipeAdapter() : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var dataRecipe: List<MealsItem> = listOf()

    inner class RecipeViewHolder(private val binding: ItemRowRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MealsItem) {
            binding.apply {
                tvRecipeTitle.text = data.strMeal

                Glide.with(ivRecipe)
                    .load(data.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivRecipe)

//                binding.cardNews.setOnClickListener {
//                    val intent = Intent(cardNews.context, DetailNewsActivity::class.java)
//                    intent.putExtra(DetailNewsActivity.EXTRA_NEWS, data)
//                    cardNews.context.startActivity(intent)
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = ItemRowRecipesBinding.inflate(layoutInflater, parent, false)
        return RecipeViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return dataRecipe.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        return holder.bind(dataRecipe[position])
    }

    fun setData(data: List<MealsItem> ){
        dataRecipe = data
        notifyDataSetChanged()
    }
}