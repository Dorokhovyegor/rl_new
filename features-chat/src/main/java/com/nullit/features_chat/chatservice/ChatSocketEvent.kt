package com.nullit.features_chat.chatservice

sealed class ChatSocketEvent() {
    object SocketMessageEvent : ChatSocketEvent()
    object SocketConnectEvent : ChatSocketEvent()
    data class SocketConnectTimeOutEvent(val message: String) : ChatSocketEvent()
    data class SocketConnectError(val message: String) : ChatSocketEvent()
    object SocketReconnectEvent : ChatSocketEvent()
    data class SocketReconnectError(val message: String) : ChatSocketEvent()
    object SocketReconnectFailed : ChatSocketEvent()
    object SocketReconnectAttempt : ChatSocketEvent()
    object SocketReconnectingEvent : ChatSocketEvent()
    object SocketPingEvent : ChatSocketEvent()
    object SocketPongEvent : ChatSocketEvent()
}