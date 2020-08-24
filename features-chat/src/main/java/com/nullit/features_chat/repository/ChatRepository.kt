package com.nullit.features_chat.repository

import com.nullit.core.repo.WrapperResponse
import com.nullit.features_chat.api.dto.SendTextMessageDto
import com.nullit.features_chat.chatservice.dto.MessageDto
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ChatRepository {
    suspend fun requestDialogListByPage(page: Int): WrapperResponse<List<DialogModel>>
    suspend fun sendMessage(message: String, chatId: Int): WrapperResponse<SendTextMessageDto>
    suspend fun connect(chatId: Int)
    suspend fun disconnect()
    suspend fun saveMessageToLocalDb(message: JSONObject)
}