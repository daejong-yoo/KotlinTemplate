package com.example.kotlintemplate.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.example.kotlintemplate.R
import com.example.kotlintemplate.extension.setOnSingleClickListener
import com.example.kotlintemplate.util.GLog

class MessageDialog(activity: Activity) : Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) {
    private lateinit var tvOk: TextView
    private lateinit var tvCancel: TextView

    constructor(activity: Activity, enableTitle: Boolean) : this(activity) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀바 삭제
        setContentView(R.layout.dialog_message)

        tvOk = findViewById(R.id.tv_ok)
        tvCancel = findViewById(R.id.tv_cancel)

        tvOk.setOnSingleClickListener {
            GLog.i("call :: tvOk")
            dismiss()
        }

        tvCancel.setOnSingleClickListener {
            GLog.i("call :: tvCancel")
            dismiss()
        }
    }
}