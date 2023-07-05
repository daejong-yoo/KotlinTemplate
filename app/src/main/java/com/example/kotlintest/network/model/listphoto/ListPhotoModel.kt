package com.example.kotlintest.network.model.listphoto

import com.example.kotlintest.base.BaseModel
import com.google.gson.annotations.SerializedName

class ListPhotoModel : BaseModel() {

    @SerializedName("data")
    var data: Data? = null

    data class Data(
        @SerializedName("mainTitle")
        var mainTitle: String? = null,

        @SerializedName("customList")
        var customList: List<CustomList>? = null
    )

    data class CustomList(
        @SerializedName("id")
        var id: String? = null,

        @SerializedName("author")
        var author: String? = null,

        @SerializedName("width")
        var width: Int? = null,

        @SerializedName("height")
        var height: Int? = null,

        @SerializedName("url")
        var url: String? = null,

        @SerializedName("download_url")
        var downloadUrl: String? = null
    )
}