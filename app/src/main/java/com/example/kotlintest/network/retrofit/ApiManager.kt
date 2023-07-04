package com.example.kotlintest.network.retrofit

import com.example.kotlintest.network.model.RetrofitModel
import retrofit2.Call

object ApiManager {

    fun getThumbnail(): Call<RetrofitModel>? {
        return RetrofitManager.getInstance().createApiService().retrofitThumbnail
    }
}