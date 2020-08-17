package com.nullit.core.persistance.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_properties")
data class UserProperties(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "firstname")
    val firstName: String?,
    @ColumnInfo(name = "lastname")
    val lastName: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "avatar")
    val avatar: String?,
    @ColumnInfo(name = "token_type")
    val token_type: String,
    @ColumnInfo(name = "token")
    val token: String
)