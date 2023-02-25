package com.artonov.recipebro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artonov.recipebro.R
import com.artonov.recipebro.databinding.ItemRowRecipesBinding
import com.artonov.recipebro.model.MealsItem
import com.artonov.recipebro.model.ResponseRecipe
import com.bumptech.glide.Glide

class RecipeAdapter() : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    private val diffCallBack = object : DiffUtil.ItemCallback<MealsItem>() {
        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    private lateinit var onItemCallBack: IOnItemCallBack

    private var dataRecipe: List<MealsItem> = listOf()

    inner class RecipeViewHolder(private val binding: ItemRowRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealsItem) {
            binding.apply {
                tvRecipeTitle.text = item.strMeal

                Glide.with(ivRecipe)
                    .load(item.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivRecipe)

                cardRecipe.setOnClickListener {
                    onItemCallBack.onItemClickCallback(item)
                }
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
        differ.submitList(data)
        dataRecipe = data
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(action: IOnItemCallBack) {
        this.onItemCallBack = action
    }

    interface IOnItemCallBack {
        fun onItemClickCallback(data: MealsItem)
    }
}