package com.example.kotlintemplate.util

import android.os.Build
import android.text.Html
import android.text.Spanned

object Util {

    fun getHtmlString(msg: String): Spanned {
        val text: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(msg)
        }

        return text
    }
}