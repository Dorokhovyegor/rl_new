package com.nullit.rtg.repository.auth

import com.google.gson.JsonObject
import com.nullit.core.persistance.entities.UserProperties
import com.nullit.rtg.api.dto.LoginResponse
import com.nullit.core.repo.WrapperResponse

interface AuthRepository {
    suspend fun attemptLogin(login: String, password: String): WrapperResponse<LoginResponse>
    suspend fun saveUserDataToDb(user: UserProperties): Long // int - result
    suspend fun attemptRegister(someData: String): WrapperResponse<JsonObject>
    suspend fun checkUserProperties(): Boolean
}