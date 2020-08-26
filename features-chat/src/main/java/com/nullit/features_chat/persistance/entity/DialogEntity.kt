package com.nullit.features_chat.persistance.entity

import androidx.room.*

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
    val lastMessage: String?
) {
    companion object{
        val PERSON_TYPE = "person"
        val GROUP_TYPE = "group"
    }
}

data class DialogWithMessage(
    @Embedded
    val message: MessageEntity,
    @Relation(parentColumn = "id", entityColumn = "chatId")
    val messages: List<MessageEntity>
)

data class DialogWithMembers(
    @Embedded
    val dialog: DialogEntity,
    @Relation(parentColumn = "id", entityColumn = "chatId")
    val members: List<MemberEntity>
)
