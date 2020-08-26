package com.nullit.features_chat.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullit.features_chat.persistance.dao.ChatMemberDao
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.persistance.dao.MessageDao
import com.nullit.features_chat.persistance.entity.DialogEntity
import com.nullit.features_chat.persistance.entity.MemberEntity
import com.nullit.features_chat.persistance.entity.MessageEntity

@Database(version = 1, entities = [DialogEntity::class, MessageEntity::class, MemberEntity::class])
abstract class ChatDataBase : RoomDatabase() {
    abstract fun getDialogDao(): DialogDao
    abstract fun getMessage(): MessageDao
    abstract fun changeMemberDao(): ChatMemberDao

    companion object {
        const val DB_NAME = "msg_db"
    }
}