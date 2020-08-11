package com.nullit.features_chat.chatservice

interface SocketEventListener {
    fun onConnect(vararg args: Any?)
    fun onDisconnect(vararg args: Any?)
    fun onConnectError(vararg args: Any?)
    fun onConnectTimeout(vararg args: Any?)
    fun onNewMessage(vararg args: Any?)
}