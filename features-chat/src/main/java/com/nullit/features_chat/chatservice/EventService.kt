package com.nullit.features_chat.chatservice

interface EventService {
    suspend fun connect(token: String, chatId: Int)
    suspend fun disconnect()
    fun setSocketEventListener(eventListener: SocketEventListener)
}