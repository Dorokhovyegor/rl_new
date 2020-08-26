package com.nullit.features_chat.utils

import com.nullit.features_chat.persistance.entity.MemberEntity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MemberFactory {

    fun generateUsersInChat(chatId: Int, count: Int): List<MemberEntity> {
        val users = ArrayList<MemberEntity>()
        for (index in 0..count) {
            users.add(generateMember(chatId))
        }
        return users
    }

    fun generateMember(chatId: Int): MemberEntity {
        return MemberEntity(
            Random.nextInt(0, 1000),
            chatId,
            "name${UUID.randomUUID()}",
            "secondname${UUID.randomUUID().toString()}",
            ""
        )
    }

}