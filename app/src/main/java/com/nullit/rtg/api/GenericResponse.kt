package com.nullit.rtg.api

import com.google.gson.annotations.SerializedName

abstract class GenericResponse {
    @SerializedName("msg") var msg: String? = null
    @SerializedName("status") val status : String? = null
}