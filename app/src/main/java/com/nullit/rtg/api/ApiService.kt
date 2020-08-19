package com.nullit.rtg.api

import com.nullit.rtg.api.dto.LoginResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/api/v1/login")
    suspend fun login(
        @Part("login") login: String,
        @Part("password") password: String
    ): LoginResponse

}

