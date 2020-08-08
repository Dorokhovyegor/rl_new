package com.nullit.rtg.api.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nullit.rtg.api.GenericResponse

data class LoginResponse(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresDate: String,
    @SerializedName("user") val user: User
) : GenericResponse()

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    @SerializedName("id")
    val userId: Int,
    @ColumnInfo(name = "login")
    @SerializedName("login") val login: String,
    @ColumnInfo(name = "firstname")
    @SerializedName("firstname") val firstName: String?,
    @ColumnInfo(name = "lastname")
    @SerializedName("lastname") val lastName: String?,
    @ColumnInfo(name = "email")
    @SerializedName("email") val email: String?,
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar") val avatar: String?
)