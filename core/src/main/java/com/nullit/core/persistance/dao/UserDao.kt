package com.nullit.core.persistance.dao

import androidx.room.*
import com.nullit.core.persistance.entities.UserProperties


@Dao
interface UserDao {

    @Query("SELECT * From user_properties")
    suspend fun requestUserInfo(): UserProperties?

    @Insert
    suspend fun insertUser(user: UserProperties): Long

    @Update
    suspend fun updateUser(user: UserProperties): Int

    @Delete
    suspend fun deleteUser(user: UserProperties): Int

}