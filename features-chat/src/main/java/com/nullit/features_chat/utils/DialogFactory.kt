package com.nullit.features_chat.utils

import com.nullit.features_chat.persistance.entity.DialogEntity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class DialogFactory {
    fun getDialogs(count: Int): List<DialogEntity> {
        val listDialogs = ArrayList<DialogEntity>()
        for (index in 1..count) {
            listDialogs.add(generateDialog())
        }
        return listDialogs
    }

    fun generateDialog(): DialogEntity {
        return DialogEntity(
            Random.nextInt(0, 1000),
            UUID.nameUUIDFromBytes(byteArrayOf(4, 5, 6)).toString(),
            if  (Random.nextDouble() > 0.5) DialogEntity.GROUP_TYPE else DialogEntity.PERSON_TYPE,
            System.currentTimeMillis().toString(),
            System.currentTimeMillis().toString(),
            ""
        )
    }
}