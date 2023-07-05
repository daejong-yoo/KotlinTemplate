package com.example.kotlintest.ui.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.base.BaseActivity
import com.example.kotlintest.common.Constant
import com.example.kotlintest.databinding.ActivityMainBinding
import com.example.kotlintest.extension.setOnSingleClickListener
import com.example.kotlintest.network.model.CustomList
import com.example.kotlintest.network.model.RetrofitModel
import com.example.kotlintest.network.retrofit.ApiManager
import com.example.kotlintest.util.GLog
import com.example.kotlintest.viewmodel.CustomListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var customListViewModel: CustomListViewModel

    override fun init() {
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.btnToFragment.setOnSingleClickListener {
            intent = Intent(mActivity, HostFragActivity::class.java)
            startActivity(intent)
        }

        binding.btnRetrofit.setOnSingleClickListener {
            requestApi()
        }
    }

    private fun initViewModel() {
        customListViewModel = ViewModelProvider(this)[CustomListViewModel::class.java]
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

    private fun requestApi() {
        ApiManager.getThumbnail()?.enqueue(object : Callback<RetrofitModel> {
            override fun onResponse(call: Call<RetrofitModel>, response: Response<RetrofitModel>) {
                if (response.isSuccessful) {
                    val model: RetrofitModel? = response.body()
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

            override fun onFailure(call: Call<RetrofitModel>, t: Throwable) {
                GLog.d("onFailure")
            }
        })
    }
}