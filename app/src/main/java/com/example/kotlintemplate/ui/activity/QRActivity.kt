package com.example.kotlintemplate.ui.activity

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseActivity
import com.example.kotlintemplate.databinding.ActivityQrBinding
import com.example.kotlintemplate.util.GLog
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DefaultDecoderFactory

class QRActivity : BaseActivity<ActivityQrBinding>(R.layout.activity_qr) {
    override fun init() {
        GLog.d("QRActivity init")
        binding.viewBarcode.run {
            barcodeView.decoderFactory =
                DefaultDecoderFactory(mutableListOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39))
            initializeFromIntent(intent)

            decodeContinuous {
                GLog.d("result : ${it.text}")
                // todo 계속 읽힘 데이터 저장 후 종료 로직 구현 필요.
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.viewBarcode.resume()
    }

    override fun onPause() {
        super.onPause()

        binding.viewBarcode.pause()
    }
}