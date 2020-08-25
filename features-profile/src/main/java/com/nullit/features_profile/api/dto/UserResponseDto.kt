package com.nullit.features_profile.api.dto

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("status") val status: String = "",
    @SerializedName("msg") val msg: String? = "",
    @SerializedName("data") val data: UserDataDto?
)

data class UserDataDto(
    @SerializedName("id") val userId: Int,
    @SerializedName("login") val login: String?,
    @SerializedName("firstname") val firstName: String?,
    @SerializedName("lastname") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("avatar") val avatarUrl: String?
)