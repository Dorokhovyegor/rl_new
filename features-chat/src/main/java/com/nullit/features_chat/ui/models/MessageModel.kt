package com.nullit.features_chat.ui.models

sealed class MessageModel(
    val messageId: Int = -1,
    val ownerId: Int = -1,
    val createdAt: String = "",
    val textMessage: String = ""
) {

    override fun equals(other: Any?): Boolean {
        return this.messageId == (other as MessageModel).messageId
    }

}

class TextMessageCurrentUser(messageId: Int, ownerId: Int, createdAt: String, textMessage: String) :
    MessageModel(messageId, ownerId, createdAt, textMessage) {

    override fun toString(): String {
        return "messageId: $messageId, textMessage: $textMessage"
    }

}

class TextMessageOtherUser(
    messageId: Int,
    ownerId: Int,
    createdAt: String,
    textMessage: String,
    val avatarUrl: String? = null
) : MessageModel(messageId, ownerId, createdAt, textMessage) {

    override fun toString(): String {
        return "messageId: $messageId, textMessage: $textMessage"
    }

}
