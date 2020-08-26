package com.nullit.features_chat.utils

import com.nullit.features_chat.persistance.entity.MessageEntity
import java.util.*
import kotlin.random.Random

class MessageFactory {

    fun generateMessages(dialogId: Int, owners: List<Int>, count: Int): ArrayList<MessageEntity> {
        val messages = ArrayList<MessageEntity>()
        for (message in 1..count) {
            messages.add(generateMessage(dialogId, owners.random()))
        }
        return messages
    }

    fun generateMessage(dialogId: Int, ownerId: Int): MessageEntity {
        return MessageEntity(
            Random.nextInt(0, 1000),
            dialogId,
            ownerId,
            "TEXT",
            false,
            System.currentTimeMillis().toString(),
            System.currentTimeMillis().toString()
        )
    }

}