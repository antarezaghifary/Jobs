package com.reza.jobs.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.reza.jobs.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String) {
    url.let {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .into(imageView)
    }
}