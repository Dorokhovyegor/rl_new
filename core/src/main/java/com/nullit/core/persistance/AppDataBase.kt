package com.nullit.core.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullit.core.persistance.dao.DialogDao
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.persistance.entities.DialogEntity
import com.nullit.core.persistance.entities.UserEntity
import com.nullit.core.persistance.entities.UserProperties

@Database(version = 2, entities = [UserProperties::class, DialogEntity::class, UserEntity::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getDialogDao(): DialogDao

    companion object {
        const val DB_NAME = "rtg_db"
    }
}