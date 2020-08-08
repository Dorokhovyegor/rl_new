package com.nullit.rtg.repository.auth

import com.google.gson.JsonObject
import com.nullit.rtg.api.dto.LoginResponse
import com.nullit.rtg.api.dto.User
import com.nullit.rtg.room.entity.UserEntity
import com.nullit.rtg.util.WrapperResponse

interface AuthRepository {

    suspend fun attemptLogin(login: String, password: String): WrapperResponse<LoginResponse>

    suspend fun saveUserDataToDb(user: UserEntity): Long // int - result

    suspend fun attemptRegister(someData: String): WrapperResponse<JsonObject>

}