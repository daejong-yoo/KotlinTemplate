package com.example.kotlintest.network.model

import java.io.Serializable

class RetrofitModel : Serializable {
    var code: String? = null
    var errMsg: String? = null
    var data: Data? = null
}