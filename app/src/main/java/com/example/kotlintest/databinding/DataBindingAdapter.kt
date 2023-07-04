/*
 * 5G eSportsLive version 01.00.01
 *
 *  Copyright �� 2019 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.example.kotlintest.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.kotlintest.extension.loadImg

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(iv: ImageView, url: String?) = url?.let {
        iv.loadImg(it)
    }
}