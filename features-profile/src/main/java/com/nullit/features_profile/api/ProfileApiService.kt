package com.nullit.features_profile.api

import com.nullit.features_profile.api.dto.UserResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ProfileApiService {
    @GET("/api/v1/users/{userId}")
    suspend fun getUserInfoById(
        @Header("Authorization") token: String,
        @Path("userId") id: Int
    ): UserResponseDto

    @PUT("/api/v1/users/{userId}")
    suspend fun updateUserData(
        @Header("Authorization") token: String,
        @Path("userId") id: Int,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String
    ): UserResponseDto
}