package com.nullit.features_chat.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nullit.features_chat.persistance.entity.MessageEntity


@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessagesIntoDialog(vararg messages: MessageEntity)

    @Query("""
        SELECT * FROM message WHERE chatId = :dialogId
    """)
    suspend fun getLastMessages(dialogId: Int): List<MessageEntity>

}