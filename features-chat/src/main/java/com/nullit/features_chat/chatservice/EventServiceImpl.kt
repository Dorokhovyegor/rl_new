package com.nullit.features_chat.chatservice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nullit.features_chat.utils.Constants
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException
import javax.inject.Inject


class EventServiceImpl
@Inject
constructor() : ChatEventService {

    lateinit var socket: Socket

    private var _socketEvent = MutableLiveData<ChatSocketEvent>()

    override val socketEvent: LiveData<ChatSocketEvent>
        get() = _socketEvent

    private fun prepareOptionsAndReturn(): IO.Options {
        // i believe it will be useful
        return IO.Options()
    }

    @SuppressWarnings("unchecked")
    override suspend fun connect(token: String, chatId: Int) {
        if (!::socket.isInitialized) {
            try {
                socket = IO.socket(Constants.SOCKET_HOST_NAME, prepareOptionsAndReturn())
                socket.connect()
                subscribeOnDefaultEvents()
                socket.emit("subscribe", JSONObject().apply {
                    put("channel", "private-chat-${chatId}")
                    put("auth", JSONObject().apply {
                        put("headers", JSONObject().apply {
                            put("Authorization", token)
                        })
                    })
                }).on("App\\Events\\ChatMessage") { message ->
                    message?.forEach {
                        Log.e("EventServiceImpl", it.toString())
                    }
                }

            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
    }

    override fun subscribeOnDefaultEvents() {
        subscribeOnConnectEvent()
        subscribeOnTimeOutEvent()
        subscribeOnConnectError()
        subscribeOnReconnectEvent()
        subscribeOnReconnectError()
        subscribeOnReconnectFailed()
        subscribeOnReconnectAttempt()
        subscribeOnReconnectingEvent()
        subscribeOnPingEvent()
        subscribeOnPonEvent()
    }

    override suspend fun disconnect() {
        socket.off()
    }

    override fun subscribeOnConnectEvent() {
        socket.on(Socket.EVENT_CONNECT) {
            _socketEvent.postValue(ChatSocketEvent.SocketConnectEvent)
        }
    }

    override fun subscribeOnTimeOutEvent() {
        socket.on(Socket.EVENT_CONNECT_TIMEOUT) {
            _socketEvent.postValue(ChatSocketEvent.SocketConnectTimeOutEvent("timeout connection"))
        }
    }

    override fun subscribeOnConnectError() {
        socket.on(Socket.EVENT_CONNECT_ERROR) {
            _socketEvent.postValue(ChatSocketEvent.SocketConnectError("connect error ${it?.get(0)}"))
        }
    }

    override fun subscribeOnReconnectEvent() {
        socket.on(Socket.EVENT_RECONNECT) {
            _socketEvent.postValue(ChatSocketEvent.SocketReconnectEvent)
        }
    }

    override fun subscribeOnReconnectError() {
        socket.on(Socket.EVENT_RECONNECT_ERROR) {
            _socketEvent.postValue(ChatSocketEvent.SocketReconnectError("reconnect error ${it?.get(0)}"))
        }
    }

    override fun subscribeOnReconnectFailed() {
        socket.on(Socket.EVENT_RECONNECT_FAILED) {
            _socketEvent.postValue(ChatSocketEvent.SocketReconnectFailed)
        }
    }

    override fun subscribeOnReconnectAttempt() {
        socket.on(Socket.EVENT_RECONNECT_ATTEMPT) {
            _socketEvent.postValue(ChatSocketEvent.SocketReconnectAttempt)
        }
    }

    override fun subscribeOnReconnectingEvent() {
        socket.on(Socket.EVENT_RECONNECTING) {
            _socketEvent.postValue(ChatSocketEvent.SocketReconnectingEvent)
        }
    }

    override fun subscribeOnPingEvent() {
        socket.on(Socket.EVENT_PING) {
            _socketEvent.postValue(ChatSocketEvent.SocketPingEvent)
        }
    }

    override fun subscribeOnPonEvent() {
        socket.on(Socket.EVENT_PONG) {
            _socketEvent.postValue(ChatSocketEvent.SocketPongEvent)
        }
    }
}