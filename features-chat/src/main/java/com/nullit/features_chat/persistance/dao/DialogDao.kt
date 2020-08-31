package com.nullit.features_chat.persistance.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.nullit.features_chat.persistance.entity.DialogEntity
import com.nullit.features_chat.persistance.entity.DialogWithMembers

@Dao
interface DialogDao {
    // paging stuff
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDialogs(dialogEntity: List<DialogEntity>)

    @Query("SELECT * FROM dialog order by updated_at desc")
    fun pagingSource(): PagingSource<Int, DialogEntity>

    @Query("DELETE FROM dialog")
    suspend fun clearAllDialogs()

}