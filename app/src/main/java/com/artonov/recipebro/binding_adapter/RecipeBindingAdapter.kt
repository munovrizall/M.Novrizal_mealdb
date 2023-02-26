package com.artonov.recipebro.binding_adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.artonov.recipebro.R
import com.artonov.recipebro.model.RecipeDetail
import com.bumptech.glide.Glide
import java.util.concurrent.Flow

object RecipeBindingAdapter {

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.ic_launcher_background
        Glide.with(imageView.context).load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }

//    @BindingAdapter("setInstruction")
//    @JvmStatic
//    fun setInstruction(textView: TextView, instructions: List<RecipeDetail?>?) {
//        val textInstruction =
//            instructions?.map { instruction -> instruction?.strInstructions }?.joinToString(
//                separator = " ",
//                limit = 3,
//                truncated = "..."
//            )
//        textView.text = textPublishers
//    }
}