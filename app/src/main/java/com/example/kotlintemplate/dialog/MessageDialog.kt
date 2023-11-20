package com.example.kotlintemplate.dialog

import android.app.ActionBar.LayoutParams
import android.app.Activity
import android.app.Dialog
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.kotlintemplate.R
import com.example.kotlintemplate.common.OnSingleClickListener
import com.example.kotlintemplate.databinding.DialogMessageBinding
import com.example.kotlintemplate.util.GLog

class MessageDialog(activity: Activity, mClickListener: OnSingleClickListener) : Dialog(activity) {
    private val binding: DialogMessageBinding by lazy { DataBindingUtil.inflate(layoutInflater, R.layout.dialog_message, null, false) }

    init {
        GLog.i("call:: TestDialog init")
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)

        binding.listener = mClickListener
        window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}