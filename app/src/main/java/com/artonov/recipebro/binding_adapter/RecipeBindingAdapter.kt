package com.artonov.recipebro.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.artonov.recipebro.R
import com.bumptech.glide.Glide

object RecipeBindingAdapter {

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.ic_launcher_background
        Glide.with(imageView.context).load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}