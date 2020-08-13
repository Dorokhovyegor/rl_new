package com.nullit.features_chat.ui.chat

import androidx.lifecycle.*
import com.nullit.features_chat.chatservice.ChatSocketEvent
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * states for
 */
const val SUCCESS_STATE = 0
const val LOADING_STATE = 1
const val ERROR_STATE = 2

class ChatViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val socketConnectionState: LiveData<Int> =
        Transformations.map((chatRepository as ChatRepositoryImpl).connectionState) {
            handleSocketChatEvent(it)
        }

    private val _loadingState = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loadingState
    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

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
        viewModelScope.launch {
            chatRepository.sendMessage(message, chatId)
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
                _loadingState.value = false
                _snackBar.value = "Подключился"
                SUCCESS_STATE
            }
            is ChatSocketEvent.SocketConnectError -> {
                _loadingState.value = false
                _snackBar.value = "Ошибка подключения"
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectAttempt -> {
                _loadingState.value = true
                _snackBar.value = "Попытка переподключения"
                LOADING_STATE
            }
            is ChatSocketEvent.SocketReconnectingEvent -> {
                _loadingState.value = true
                _snackBar.value = "Переподключение"
                LOADING_STATE
            }
            is ChatSocketEvent.SocketReconnectError -> {
                _loadingState.value = false
                _snackBar.value = "Ошибка переподключения ${socketEvent.message}"
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectFailed -> {
                _loadingState.value = false
                _snackBar.value = "Ошибка переподключение"
                ERROR_STATE
            }
            is ChatSocketEvent.SocketReconnectEvent -> {
                _loadingState.value = false
                _snackBar.value = "Переподключился"
                SUCCESS_STATE
            }
            is ChatSocketEvent.SocketConnectTimeOutEvent -> {
                _loadingState.value = false
                _snackBar.value = "Таймаут"
                ERROR_STATE
            }
            is ChatSocketEvent.SocketPingEvent -> {
                _loadingState.value = true
                LOADING_STATE
            }
            is ChatSocketEvent.SocketPongEvent -> {
                _loadingState.value = false
                SUCCESS_STATE
            }
            else -> {
                _loadingState.value = false
                _snackBar.value = null
                ERROR_STATE
            }
        }
    }
}