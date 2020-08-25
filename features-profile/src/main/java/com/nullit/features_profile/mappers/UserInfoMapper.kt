package com.nullit.features_profile.mappers

import com.nullit.core.StringProvider
import com.nullit.features_profile.api.dto.UserResponseDto
import com.nullit.features_profile.ui.presentationmodel.UserPresentationModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoMapper
@Inject
constructor(private val stringProvider: StringProvider) {

    fun userFromDtoToPresentationModel(userResponseDto: UserResponseDto): UserPresentationModel {
        val firstName =
            if (userResponseDto.data?.firstName == null) "отсутвует" else userResponseDto.data.firstName
        val lastName =
            if (userResponseDto.data?.lastName == null) "отсутствует" else userResponseDto.data.lastName
        val email =
            if (userResponseDto.data?.email == null) "отсутствует" else userResponseDto.data.email
        val avatar =
            if (userResponseDto.data?.avatarUrl == null) "https://cdn-images-1.medium.com/fit/c/200/200/1*Y7CSgrL0qg2ppuKorh-Vvw.jpeg" else userResponseDto.data.avatarUrl

        val name = firstName + " " + lastName

        return UserPresentationModel(
            name,
            email,
            avatar
        )
    }

}