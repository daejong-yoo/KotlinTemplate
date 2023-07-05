package com.example.kotlintest.network.retrofit

import com.example.kotlintest.network.model.listphoto.ListPhotoModel
import com.example.kotlintest.network.model.singlePhoto.SinglePhotoModel
import retrofit2.Call

object ApiManager {

    fun requestSinglePhoto(): Call<SinglePhotoModel> {
        return RetrofitManager.getInstance().createApiService().getSinglePhoto
    }

    fun requestListPhoto(): Call<ListPhotoModel> {
        return RetrofitManager.getInstance().createApiService().getListPhoto
    }
}