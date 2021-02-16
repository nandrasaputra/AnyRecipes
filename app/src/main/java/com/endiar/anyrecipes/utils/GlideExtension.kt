package com.endiar.anyrecipes.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.R

fun Context.loadImage(url: String, view: ImageView) = Glide.with(this)
    .load(url)
    .placeholder(R.drawable.background_placeholder)
    .into(view)