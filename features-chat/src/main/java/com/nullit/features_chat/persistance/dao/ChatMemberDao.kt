package com.nullit.features_chat.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nullit.features_chat.persistance.entity.MemberEntity
import com.nullit.features_chat.persistance.entity.MessageEntity

@Dao
interface ChatMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemberIntoDialog(member: MemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembersIntoDialog(members: List<MemberEntity>): List<Long>

    @Query(
        """
        SELECT * FROM chat_members WHERE chatId = :dialogId
    """
    )
    suspend fun getMembers(dialogId: Int): List<MemberEntity>

}