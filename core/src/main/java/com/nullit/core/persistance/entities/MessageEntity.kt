package com.nullit.core.persistance.entities

import androidx.room.Entity

@Entity
data class MessageEntity(
    val message: String
)