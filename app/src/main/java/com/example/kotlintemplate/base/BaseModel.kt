package com.example.kotlintemplate.base

import java.io.Serializable

abstract class BaseModel : Serializable {
    var code: String? = null
    var errMsg: String? = null
}