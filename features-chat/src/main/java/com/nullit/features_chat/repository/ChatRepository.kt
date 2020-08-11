package com.nullit.features_chat.repository

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ChatRepository {
    fun sendMessage(message: String, chatId: Int)
    fun connect(chatId: Int)
    fun disconnect(chatId: Int)
    suspend fun subscribeOnMessages(): Flow<JSONObject>
    suspend fun saveMessageToLocalDb(message: JSONObject)
}