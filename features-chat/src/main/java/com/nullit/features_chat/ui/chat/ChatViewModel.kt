package com.nullit.features_chat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.features_chat.chatservice.ChatSocketEvent
import com.nullit.features_chat.mappers.MessageMapper
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import com.nullit.features_chat.ui.models.MessageModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    private val chatRepository: ChatRepository,
    private val sharedPrefsManager: SharedPrefsManager,
    private val messageMapper: MessageMapper
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

    @ExperimentalCoroutinesApi
    fun messageFlow(): Flow<MessageModel> {
        return (chatRepository as ChatRepositoryImpl).messageFlow().map {
            val userId = sharedPrefsManager.getUserId()
            messageMapper.fromMessageDtoToMessageModel(it, userId)
        }
    }

    fun sendMessage(messageText: String, chatId: Int) {
        if (messageText.isNotBlank()) {
            val sendMessageJob = viewModelScope.launch {
                _loading.value = true
                if (!messageText.trim().isEmpty()) {
                    val result = chatRepository.sendMessage(messageText, chatId)
                    if (result is WrapperResponse.NetworkError || result is WrapperResponse.GenericError<*>) {
                        _snackBar.value = "Не удалось отправить сообщение"
                    }
                }
            }
            sendMessageJob.invokeOnCompletion {
                _loading.value = false
            }
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