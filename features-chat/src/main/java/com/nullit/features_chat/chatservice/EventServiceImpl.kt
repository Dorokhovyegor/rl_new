package com.nullit.features_chat.chatservice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.nullit.core.utils.buildJSONForSocketConnection
import com.nullit.features_chat.chatservice.dto.MessageDto
import com.nullit.features_chat.utils.Constants
import com.nullit.features_chat.utils.Constants.Companion.EVENT_NEW_MESSAGE
import com.nullit.features_chat.utils.Constants.Companion.EVENT_SUBSCRIBE_WITH_TOKEN
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import java.net.URISyntaxException
import javax.inject.Inject


class EventServiceImpl
@Inject
constructor(
    private val gson: Gson,
    private val jsonParser: JsonParser
) : ChatEventService {

    lateinit var socket: Socket
    private var _socketEvent = MutableLiveData<ChatSocketEvent>()
    override val socketEvent: LiveData<ChatSocketEvent>
        get() = _socketEvent

    private var chatId: Int? = null
    private var token: String? = null

    private fun prepareOptionsAndReturn(): IO.Options {
        // i believe it will be useful
        return IO.Options()
    }

    override suspend fun connect(token: String, chatId: Int) {
        this.chatId = chatId
        this.token = token
        try {
            socket = IO.socket(Constants.SOCKET_HOST_NAME, prepareOptionsAndReturn())
            socket.connect()
            subscribeOnDefaultEvents()
            socket.emit(
                EVENT_SUBSCRIBE_WITH_TOKEN,
                JSONObject().buildJSONForSocketConnection(token, chatId)
            )
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    @ExperimentalCoroutinesApi
    override fun messageFlow(): Flow<MessageDto> = callbackFlow {
        val callback = object : SocketCallBack<MessageDto> {
            override fun onNext(msg: MessageDto) {
                offer(msg)
            }

            override fun onError(t: Throwable) {
                cancel(CancellationException("API Error"))
            }
        }
        socket.on(EVENT_NEW_MESSAGE) { message ->
            try {
                val jsonString = jsonParser.parse(message[1].toString())
                val model = gson.fromJson<MessageDto>(jsonString, MessageDto::class.java)
                callback.onNext(model)
            } catch (e: JsonSyntaxException) {
                callback.onError(e)
                e.printStackTrace()
            }
        }
        awaitClose {
            socket.disconnect()
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
            if (token != null && chatId != null) {
                socket.emit(
                    EVENT_SUBSCRIBE_WITH_TOKEN,
                    JSONObject().buildJSONForSocketConnection(token!!, chatId!!)
                )
            }
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

interface SocketCallBack<DataClass> {
    fun onNext(msg: DataClass)
    fun onError(t: Throwable)
}
