package com.nullit.features_chat.repository

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

class ChatRepositoryImpl: ChatRepository {

    override fun sendMessage(message: String, chatId: Int) {
        // afther success save message
    }

    override fun connect(chatId: Int) {

    }

    override fun disconnect(chatId: Int) {

    }

    override suspend fun subscribeOnMessages(): Flow<JSONObject> {
        TODO("Not yet implemented")
        // save every message to local db
    }

    override suspend fun saveMessageToLocalDb(message: JSONObject) {

    }
}