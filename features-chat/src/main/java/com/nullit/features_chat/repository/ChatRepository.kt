package com.nullit.features_chat.repository

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ChatRepository {
    suspend fun sendMessage(message: String, chatId: Int)
    suspend fun connect(chatId: Int)
    suspend fun disconnect(chatId: Int)
    suspend fun subscribeOnMessages(): Flow<JSONObject>
    suspend fun saveMessageToLocalDb(message: JSONObject)
}