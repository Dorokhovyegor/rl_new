package com.nullit.features_chat.chatservice

interface ChatEventService: EventService {
    fun subscribeOnConnectEvent()
    fun subscribeOnTimeOutEvent()
    fun subscribeOnConnectError()
    fun subscribeOnReconnectEvent()
    fun subscribeOnReconnectError()
    fun subscribeOnReconnectFailed()
    fun subscribeOnReconnectAttempt()
    fun subscribeOnReconnectingEvent()
    fun subscribeOnPingEvent()
    fun subscribeOnPonEvent()
}
