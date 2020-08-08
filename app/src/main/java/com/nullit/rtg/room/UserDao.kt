package com.nullit.rtg.room

import androidx.room.*
import com.nullit.rtg.room.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * From userentity")
    suspend fun requestUserInfo(): UserEntity

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Delete
    suspend fun deleteUser(user: UserEntity): Int

}