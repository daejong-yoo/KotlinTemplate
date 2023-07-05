package com.example.kotlintest.network.retrofit

import com.example.kotlintest.network.model.listphoto.ListPhotoModel
import com.example.kotlintest.network.model.singlePhoto.SinglePhotoModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApiService {
    //GET 방식으로 요청하고 baseURL 뒤에 붙을 주소 지정--retrofit
    @get:GET("4I1S")
    val getSinglePhoto: Call<SinglePhotoModel>

    @get:GET("BGEN")
    val getListPhoto: Call<ListPhotoModel>
}