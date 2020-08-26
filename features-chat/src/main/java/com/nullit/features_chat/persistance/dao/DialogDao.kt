package com.nullit.features_chat.persistance.dao

import androidx.room.*
import com.nullit.features_chat.persistance.entity.DialogEntity
import com.nullit.features_chat.persistance.entity.DialogWithMembers

@Dao
interface DialogDao {

    @Transaction
    @Query("SELECT * FROM dialog")
    suspend fun getDialogs(): List<DialogWithMembers>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDialogWithMembers(dialogEntity: DialogEntity): Long

}