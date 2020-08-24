package com.nullit.features_chat.chatservice.dto

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("data") val dataMessage: MessageInfo
)

data class MessageInfo(
    @SerializedName("message") val message: Message
)

data class Message(
    @SerializedName("type") val type: String,
    @SerializedName("text") val textMessage: String,
    @SerializedName("chat_id") val chatId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val messageId: Int,
    @SerializedName("files") val files: List<Any>? = null,
    @SerializedName("images") val images: List<Any>? = null,
    @SerializedName("reply_messages") val replyMessages: List<Any>? = null,
    @SerializedName("readed") val readed: Boolean,
    @SerializedName("favorite") val favorite: Any? = null
)