package com.example.kotlintemplate.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.kotlintemplate.extension.loadImg

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(iv: ImageView, url: String?) = url?.let {
        iv.loadImg(it)
    }
}