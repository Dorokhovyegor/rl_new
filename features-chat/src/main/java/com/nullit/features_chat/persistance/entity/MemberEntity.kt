package com.nullit.features_chat.persistance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    @ColumnInfo(name = "chatId")
    val chatId: Int,
    val firstName: String,
    val secondName: String,
    val avatar: String
)