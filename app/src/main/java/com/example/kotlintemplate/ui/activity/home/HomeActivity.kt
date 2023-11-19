package com.example.kotlintemplate.ui.activity.home

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseActivity
import com.example.kotlintemplate.databinding.ActivityHomeBinding
import com.example.kotlintemplate.util.GLog

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun init() {
        GLog.i("call :: HomeActivity")
    }
}