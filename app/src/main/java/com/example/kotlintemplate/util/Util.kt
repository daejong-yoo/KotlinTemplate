package com.example.kotlintemplate.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics


object Util {

    fun getHtmlString(msg: String): Spanned {
        val text: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(msg)
        }

        return text
    }

    //dp를 px로 변환 (dp를 입력받아 px을 리턴)
    fun dpToPx(context: Context, dp: Int): Int {
        val resources: Resources = context.getResources()
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    //px을 dp로 변환 (px을 입력받아 dp를 리턴)
    fun pxToDp(context: Context, px: Int): Int {
        val resources: Resources = context.getResources()
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}