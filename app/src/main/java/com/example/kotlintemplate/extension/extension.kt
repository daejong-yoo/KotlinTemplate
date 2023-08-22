package com.example.kotlintemplate.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kotlintemplate.common.OnSingleClickListener

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.isVisibility(): Boolean {
    return this.visibility == View.VISIBLE
}


fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val singleClick = OnSingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(singleClick)
}

// ImageView 확장 함수
fun ImageView.loadImg(url: String) = Glide.with(this.context).load(url).into(this)