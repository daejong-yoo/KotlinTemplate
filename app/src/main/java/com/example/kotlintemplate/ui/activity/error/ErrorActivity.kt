package com.example.kotlintemplate.ui.activity.error

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseActivity
import com.example.kotlintemplate.databinding.ActivityErrorBinding
import com.example.kotlintemplate.util.GLog

class ErrorActivity : BaseActivity<ActivityErrorBinding>(R.layout.activity_error) {
    override fun init() {
        GLog.i("call:: ErrorActivity")
    }
}