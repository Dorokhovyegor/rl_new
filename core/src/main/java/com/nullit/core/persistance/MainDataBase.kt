package com.nullit.core.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.persistance.entities.UserProperties

@Database(version = 3, entities = [UserProperties::class])
abstract class MainDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        const val DB_NAME = "rtg_db"
    }
}