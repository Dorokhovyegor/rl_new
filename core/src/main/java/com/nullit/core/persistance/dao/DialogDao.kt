package com.nullit.core.persistance.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.nullit.core.persistance.entities.DialogWithUser

@Dao
interface DialogDao {
    @Transaction
    @Query("SELECT * FROM dialog")
    suspend fun getDialogs(): List<DialogWithUser>
}