package com.nullit.rtg.api.responses

import com.google.gson.annotations.SerializedName
import com.nullit.rtg.ui.state.Data

data class GenericResponse(
    @SerializedName("msg") val msg: String?,
    @SerializedName("status") val status : String?,
    @SerializedName("data") val data: Any?
)