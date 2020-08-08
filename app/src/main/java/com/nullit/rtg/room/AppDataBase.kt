package com.nullit.rtg.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullit.rtg.room.entity.UserEntity

@Database(version = 1, entities = [UserEntity::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        const val DB_NAME = "rtg_db"
    }
}