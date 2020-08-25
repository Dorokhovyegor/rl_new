package com.nullit.features_profile.repository

import com.nullit.core.generateBearerToken
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.repo.JobManager
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.features_profile.api.ProfileApiService
import com.nullit.features_profile.mappers.UserInfoMapper
import com.nullit.features_profile.ui.presentationmodel.UserPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class ProfileRepositoryImpl
@Inject
constructor(
    private val apiService: ProfileApiService,
    private val userDao: UserDao,
    private val userInfoMapper: UserInfoMapper,
    private val sharedPrefsManager: SharedPrefsManager
) : JobManager(), ProfileRepository {

    private var token: String? = null

    override suspend fun requestUserData(): WrapperResponse<UserPresentationModel> {
        val wrapperResponse = safeApiCall(dispatcher = Dispatchers.IO) {
            if (token == null) token = userDao.requestUserInfo()?.token?.generateBearerToken()
            val userId = sharedPrefsManager.getUserId()
            apiService.getUserInfoById(token!!, userId)
        }

        return when (wrapperResponse) {
            is WrapperResponse.SuccessResponse -> {
                val mappedResult = withContext(Dispatchers.Default) {
                    userInfoMapper.userFromDtoToPresentationModel(wrapperResponse.body)
                }
                WrapperResponse.SuccessResponse(mappedResult)
            }
            is WrapperResponse.NetworkError -> {
                wrapperResponse as WrapperResponse.NetworkError<UserPresentationModel>
            }
            is WrapperResponse.GenericError<*> -> {
                wrapperResponse as WrapperResponse.GenericError<UserPresentationModel>
            }
        }
    }

    override suspend fun updateUserData(
        name: String,
        lastName: String
    ): WrapperResponse<UserPresentationModel> {
        val wrapperResponse = safeApiCall(Dispatchers.IO) {
            if (token == null) token = userDao.requestUserInfo()?.token?.generateBearerToken()
            val userId = sharedPrefsManager.getUserId()
            apiService.updateUserData(token!!, userId, name, lastName)
        }

        return when (wrapperResponse) {
            is WrapperResponse.SuccessResponse -> {
                val mappedResult = withContext(Dispatchers.Default) {
                    userInfoMapper.userFromDtoToPresentationModel(wrapperResponse.body)
                }
                WrapperResponse.SuccessResponse(mappedResult)
            }
            is WrapperResponse.NetworkError -> {
                wrapperResponse as WrapperResponse.NetworkError<UserPresentationModel>
            }
            is WrapperResponse.GenericError<*> -> {
                wrapperResponse as WrapperResponse.GenericError<UserPresentationModel>
            }
        }
    }

    override suspend fun logOut(): Int {
        return userDao.deleteUser()
    }
}