package com.nullit.features_chat.chatservice

import java.net.URISyntaxException

interface EventService {

    @Throws(URISyntaxException::class)
    fun connect(token: String, chatId: Int)

    fun disconnect()

    fun setSocketEventListener(eventListener: SocketEventListener)

}