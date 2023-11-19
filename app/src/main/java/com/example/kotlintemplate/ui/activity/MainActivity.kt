package com.example.kotlintemplate.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseActivity
import com.example.kotlintemplate.common.Constant
import com.example.kotlintemplate.databinding.ActivityMainBinding
import com.example.kotlintemplate.extension.setOnSingleClickListener
import com.example.kotlintemplate.network.model.singlePhoto.CustomList
import com.example.kotlintemplate.network.model.singlePhoto.SinglePhotoModel
import com.example.kotlintemplate.network.retrofit.ApiManager
import com.example.kotlintemplate.ui.activity.home.HomeActivity
import com.example.kotlintemplate.util.GLog
import com.example.kotlintemplate.viewmodel.CustomListViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val customListViewModel: CustomListViewModel by lazy { ViewModelProvider(this)[CustomListViewModel::class.java] }
    private val mBarCodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mActivity, "Scanned : " + result.contents, Toast.LENGTH_SHORT).show()
        }
    }

    override fun init() {
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.btnToHome.setOnSingleClickListener {
            intent = Intent(mActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.btnToFragment.setOnSingleClickListener {
            intent = Intent(mActivity, HostFragActivity::class.java)
            startActivity(intent)
        }

        binding.btnRetrofit.setOnSingleClickListener {
            requestApi()
        }

        binding.btnQr.setOnSingleClickListener {
            GLog.i("qr button onClick")
            intent = Intent(mActivity, QRActivity::class.java)
            startActivity(intent)
        }

        binding.btnDialog.setOnSingleClickListener {
            GLog.i("dialog button onClick")
//            MessageDialog(mActivity).show()
            val configuration = Configuration(resources.configuration)
            configuration.setLocale(Locale(Locale.US.toString()))

            intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.btnToRoom.setOnSingleClickListener {
            intent = Intent(mActivity, RoomActivity::class.java)
            startActivity(intent)
        }

        GLog.d("language : ${Locale.getISOLanguages()}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GLog.d("language 2 : ${resources.configuration.locales[0].language}")
            GLog.d("language size :${resources.configuration.locales.size()}")
        }
    }

    private fun initViewModel() {
        binding.vm = customListViewModel
    }

    private fun updateViewModel(list: List<CustomList>?) {
        if (!list.isNullOrEmpty()) {
            val gameTitle = list[0].gameTitle
            val imgUrl = list[0].imageUrl

            gameTitle?.let {
                customListViewModel.updateGameTitle(it)
            }

            imgUrl?.let {
                customListViewModel.updateImgUrl(it)
            }
        }
    }

    private fun onScanButtonClick() {
        mBarCodeLauncher.launch(ScanOptions())
    }

    private fun requestApi() {
        ApiManager.requestSinglePhoto().enqueue(object : Callback<SinglePhotoModel> {
            override fun onResponse(
                call: Call<SinglePhotoModel>,
                response: Response<SinglePhotoModel>
            ) {
                if (response.isSuccessful) {
                    val model: SinglePhotoModel? = response.body()
                    model?.let {
                        val errMsg: String? = model.errMsg

                        when (model.code) {
                            Constant.CODE_SUCCESS -> {

                                model.data?.let {
                                    updateViewModel(it.customList)

                                } ?: run {
                                    GLog.e("data is null")
                                }
                            }
                            else -> {
                                GLog.e("실패 : $errMsg")
                            }
                        }
                    } ?: run {
                        GLog.e("model is null")
                    }
                }
            }

            override fun onFailure(call: Call<SinglePhotoModel>, t: Throwable) {
                GLog.d("onFailure")
            }
        })
    }
}