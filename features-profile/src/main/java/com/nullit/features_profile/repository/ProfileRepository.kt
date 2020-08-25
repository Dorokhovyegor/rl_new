package com.nullit.features_profile.repository

import com.nullit.core.repo.WrapperResponse
import com.nullit.features_profile.ui.presentationmodel.UserPresentationModel

interface ProfileRepository {
    suspend fun requestUserData(): WrapperResponse<UserPresentationModel>
    suspend fun updateUserData(name: String, lastName: String): WrapperResponse<UserPresentationModel>
    suspend fun logOut(): Int
}