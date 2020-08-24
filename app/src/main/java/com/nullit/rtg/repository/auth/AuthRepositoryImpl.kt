package com.nullit.rtg.repository.auth

import com.google.gson.JsonObject
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.persistance.entities.UserProperties
import com.nullit.core.repo.JobManager
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.rtg.api.ApiService
import com.nullit.rtg.api.dto.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl
@Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sharedPrefsManager: SharedPrefsManager
) : JobManager(), AuthRepository {

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

    override suspend fun checkUserProperties(): Boolean {
        val userInfo = userDao.requestUserInfo()
        return userInfo != null && userInfo.token.isNotEmpty()
    }

    override suspend fun saveUserDataToDb(user: UserProperties): Long {
        sharedPrefsManager.saveUserProperties(user)
        return userDao.insertUser(user = user)
    }
}