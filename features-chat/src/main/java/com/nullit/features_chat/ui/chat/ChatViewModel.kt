package com.nullit.features_chat.ui.chat

import android.util.Log
import androidx.lifecycle.*
import com.nullit.core.StringProvider
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.features_chat.chatservice.ChatSocketEvent
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * states for socket connection
 */
const val SUCCESS_STATE = 0
const val LOADING_STATE = 1
const val ERROR_STATE = 2

class ChatViewModel
@Inject
constructor(
    private val stringProvider: StringProvider,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    val socketConnectionState: LiveData<Int> =
        Transformations.map((chatRepository as ChatRepositoryImpl).connectionState) {
            handleSocketChatEvent(it)
        }

    fun connect(chatId: Int?) {
        viewModelScope.launch {
            chatId?.let { chat ->
                chatRepository.connect(chatId)
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            chatRepository.disconnect()
        }
    }

    fun sendMessage(message: String, chatId: Int) {
        Log.e("ChatViewModel", "call method $message, $chatId")
        val sendMessageJob = viewModelScope.launch {
            _loading.value = true
            if (message.trim().isEmpty()) {
                _snackBar.value = "Введите текст"
            } else {
                val result = chatRepository.sendMessage(message, chatId)
                if (result is WrapperResponse.NetworkError || result is WrapperResponse.GenericError<*>) {
                    _snackBar.value = "Не удалось отправить сообщение"
                }
            }
        }
        sendMessageJob.invokeOnCompletion {
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            chatRepository.disconnect()
        }
    }

    private fun handleSocketChatEvent(socketEvent: ChatSocketEvent): Int {
        return when (socketEvent) {
            is ChatSocketEvent.SocketConnectEvent -> {
                _loading.value = false
                SUCCESS_STATE
            }
            is ChatSocketEvent.SocketConnectError -> {
                _loading.value = false
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectAttempt -> {
                _loading.value = true
                LOADING_STATE
            }
            is ChatSocketEvent.SocketReconnectingEvent -> {
                _loading.value = true
                LOADING_STATE
            }
            is ChatSocketEvent.SocketReconnectError -> {
                _loading.value = false
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectFailed -> {
                _loading.value = false
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectEvent -> {
                _loading.value = false
                SUCCESS_STATE
            }
            is ChatSocketEvent.SocketConnectTimeOutEvent -> {
                _loading.value = false
                ERROR_STATE
            }
            is ChatSocketEvent.SocketPingEvent -> {
                _loading.value = true
                LOADING_STATE
            }
            is ChatSocketEvent.SocketPongEvent -> {
                _loading.value = false
                SUCCESS_STATE
            }
            else -> {
                _loading.value = false
                _snackBar.value = null
                ERROR_STATE
            }
        }
    }
}