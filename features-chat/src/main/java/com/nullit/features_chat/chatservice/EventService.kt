package com.nullit.features_chat.chatservice

import androidx.lifecycle.LiveData
import com.nullit.features_chat.chatservice.dto.MessageDto
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface EventService {
    val socketEvent: LiveData<ChatSocketEvent>
    suspend fun connect(token: String, chatId: Int)
    suspend fun disconnect()
    fun subscribeOnDefaultEvents()
}