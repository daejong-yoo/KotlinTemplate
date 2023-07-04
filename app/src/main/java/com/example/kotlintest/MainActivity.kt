package com.example.kotlintest

import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.base.BaseActivity
import com.example.kotlintest.databinding.ActivityMainBinding
import com.example.kotlintest.extension.loadImg
import com.example.kotlintest.extension.toGone
import com.example.kotlintest.network.model.CustomList
import com.example.kotlintest.network.model.RetrofitModel
import com.example.kotlintest.network.retrofit.ApiManager
import com.example.kotlintest.util.GLog
import com.example.kotlintest.util.Util
import com.example.kotlintest.viewmodel.RetrofitViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var viewModel: RetrofitViewModel

    override fun init() {
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.btnTest.setOnClickListener {
            ApiManager.getThumbnail()?.enqueue(object : Callback<RetrofitModel> {
                override fun onResponse(call: Call<RetrofitModel>, response: Response<RetrofitModel>) {
                    if (response.isSuccessful) {
                        val model: RetrofitModel? = response.body()
                        model?.let {
                            val errMsg: String? = model.errMsg

                            when (model.code) {
                                "0000" -> {
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[RetrofitViewModel::class.java]

        viewModel.list.observe(this) {
            if (!it.isNullOrEmpty()) {
                val imgUrl: String? = it[0].imageUrl
                val mainTitle: String? = it[0].gameTitle

                imgUrl?.let {
                    binding.imgGame.loadImg(imgUrl)
                }

                mainTitle?.let {
                    binding.btnTest.text = mainTitle
                }
            }
        }
    }

    private fun updateViewModel(list: List<CustomList>?) {
        if (!list.isNullOrEmpty()) {
            viewModel.updateListItem(list)
        }
    }
}