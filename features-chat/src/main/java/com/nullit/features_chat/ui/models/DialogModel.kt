package com.nullit.features_chat.ui.models

data class DialogModel (
    val dialogId: Int,
    val title: String,
    val lastMessage: String?,
    val timeOfLastMessage: String?,
    val avatarUri: String
)