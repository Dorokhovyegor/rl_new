package com.nullit.features_chat.api.dto

import com.google.gson.annotations.SerializedName

data class SendTextMessageDto(
    @SerializedName("id") val messageId: Int,
    @SerializedName("chat_id") val chatId: Int,
    @SerializedName("user_id") val ownerId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("text") val msgText: String
)