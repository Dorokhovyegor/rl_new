package com.nullit.core.persistance.entities

import androidx.room.*

data class DialogWithUser(
    @Embedded val dialog: DialogEntity,
    @Relation(parentColumn = "id", entityColumn = "chatId")
    val users: List<UserEntity>
)

@Entity(tableName = "dialog")
data class DialogEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val chatId: Int,
    @ColumnInfo(name = "name")
    val title: String,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    @ColumnInfo(name = "last_message")
    val lastMessage: String
)

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val chatId: Int,
    val firstName: String,
    val secondName: String,
    val avatar: String
)