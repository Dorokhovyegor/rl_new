package com.nullit.features_chat.repository

import com.nullit.features_chat.chatservice.EventService
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

class ChatRepositoryImpl(
    val eventService: EventService
    // val tokenDao: TokenDao
    // val messageDao: MessageDao
): ChatRepository {

    override suspend fun sendMessage(message: String, chatId: Int) {
        // after success save message
    }

    override suspend fun connect(chatId: Int) {
        // val token = RequestToken()
        val token: String = "token"
        eventService.connect(token, chatId)
    }

    override suspend fun disconnect(chatId: Int) {
        eventService.disconnect()
    }

    override suspend fun subscribeOnMessages(): Flow<JSONObject> {
        TODO("Not yet implemented")
        // save every message to local db
    }

    override suspend fun saveMessageToLocalDb(message: JSONObject) {
        // messageDao.saveMessageToDb
    }

}