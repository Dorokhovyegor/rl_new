package com.nullit.features_chat.api.dto

import com.google.gson.annotations.SerializedName

data class DialogListDto(
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val dialogList: List<DialogDto>,
    @SerializedName("total") val total: Int
)

data class DialogDto(
    @SerializedName("id") val dialogId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val typeChat: String, // personal, group
    @SerializedName("updated_at") val lastUpdate: String,
    @SerializedName("users") val userList: List<UserDto>,
    @SerializedName("latest_message") val lastMessage: LastMessageDto?
)

data class LastMessageDto(
    @SerializedName("type") val typeMessage: String, // TEXT, ...
    @SerializedName("text") val textMessage: String?, // null if type != TEXT
    @SerializedName("readed") val read: Boolean
)

data class UserDto(
    @SerializedName("firstname") val userName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("avatar") val avatar: String?
)