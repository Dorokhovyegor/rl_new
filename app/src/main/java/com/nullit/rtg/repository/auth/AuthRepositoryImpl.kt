package com.nullit.rtg.repository.auth

import com.google.gson.JsonObject
import com.nullit.rtg.api.ApiService
import com.nullit.rtg.api.dto.LoginResponse
import com.nullit.rtg.api.dto.User
import com.nullit.rtg.repository.JobManager
import com.nullit.rtg.room.UserDao
import com.nullit.rtg.room.entity.UserEntity
import com.nullit.rtg.util.WrapperResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl
@Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : JobManager("AuthRepositoryImpl"), AuthRepository {

    override suspend fun attemptLogin(
        login: String,
        password: String
    ): WrapperResponse<LoginResponse> {
        return safeApiCall(dispatcher) {
            apiService.login(login, password)
        }
    }

    override suspend fun attemptRegister(someData: String): WrapperResponse<JsonObject> {
        return WrapperResponse.create<JsonObject>(Throwable(""))
    }

    override suspend fun saveUserDataToDb(user: UserEntity): Long {
       return userDao.insertUser(user = user)
    }
}