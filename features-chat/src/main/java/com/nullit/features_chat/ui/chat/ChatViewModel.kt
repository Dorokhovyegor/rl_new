package com.nullit.features_chat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullit.features_chat.repository.ChatRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _connectionState = MutableLiveData<Int>()
    // 0 - disconnected
    // 1 - loading
    // 2 - connected

    val connectionState: LiveData<Int>
        get() = _connectionState

    fun connect(chatId: Int?) {
        _connectionState.value = 1
        val job = viewModelScope.launch {
            chatId?.let {chat ->
                chatRepository.connect(chatId)
            }
        }

        job.invokeOnCompletion {
            _connectionState.value = 0
        }
    }

    fun disconnect(chatId: Int) {
        _connectionState.value = 1
        val job = viewModelScope.launch {
            chatRepository.disconnect(chatId)
        }

        job.invokeOnCompletion {
            _connectionState.value = 0
        }
    }

    fun sendMessage(message: String, chatId: Int) {
        viewModelScope.launch {
            chatRepository.sendMessage(message, chatId)
        }
    }
}