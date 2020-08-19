package com.nullit.features_chat.repository

import com.nullit.core.repo.WrapperResponse
import com.nullit.features_chat.api.dto.DialogDto
import com.nullit.features_chat.api.dto.SendTextMessageDto
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ChatRepository {
    suspend fun requestDialogListByPage(page: Int): List<DialogModel>
    suspend fun sendMessage(message: String, chatId: Int): WrapperResponse<SendTextMessageDto>
    suspend fun connect(chatId: Int)
    suspend fun disconnect()
    suspend fun subscribeOnMessages(): Flow<JSONObject>
    suspend fun saveMessageToLocalDb(message: JSONObject)
}