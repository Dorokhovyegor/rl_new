package com.nullit.rtg.mappers

import com.nullit.core.StringProvider
import com.nullit.core.generatePathToDrawable
import com.nullit.core.persistance.entities.UserProperties
import com.nullit.rtg.R
import com.nullit.rtg.api.dto.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper
@Inject
constructor(
    private val stringProvider: StringProvider
) {

    companion object {

    }

    fun fromLoginResponseToUserProperties(loginResponse: LoginResponse): UserProperties {
        return UserProperties(
            loginResponse.loginData.user.userId,
            loginResponse.loginData.user.login,
            if (  loginResponse.loginData.user.firstName.isNullOrBlank()) stringProvider.provideString(R.string.undefined_text) else   loginResponse.loginData.user.firstName,
            if (  loginResponse.loginData.user.lastName.isNullOrBlank()) stringProvider.provideString(R.string.undefined_text) else   loginResponse.loginData.user.lastName,
            if (  loginResponse.loginData.user.email.isNullOrBlank()) stringProvider.provideString(R.string.undefined_text) else   loginResponse.loginData.user.email,
            if (  loginResponse.loginData.user.avatar.isNullOrBlank()) R.drawable.ic_default_avatar.generatePathToDrawable() else   loginResponse.loginData.user.avatar,
            loginResponse.loginData.tokenType,
            loginResponse.loginData.token
        )
    }
}