package com.nullit.features_chat.persistance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val messageId: Int,
    val chatId: Int,
    val messageOwnerId: Int,
    val type: String,
    val deleted: Boolean,
    val createdAt: String,
    val updatedAt: String
)