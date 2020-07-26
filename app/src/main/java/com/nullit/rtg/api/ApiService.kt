package com.nullit.rtg.api

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import com.nullit.rtg.util.GenericApiResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/api/v1/login")
    fun login(
        @Part("login") login: String,
        @Part("password") password: String
    ): LiveData<GenericApiResponse<JsonObject>>

}

