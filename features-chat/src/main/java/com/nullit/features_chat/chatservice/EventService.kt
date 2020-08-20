package com.nullit.features_chat.chatservice

import androidx.lifecycle.LiveData

interface EventService {
    val socketEvent: LiveData<ChatSocketEvent>
    suspend fun connect(token: String, chatId: Int)
    suspend fun disconnect()
    fun subscribeOnDefaultEvents()
}