package com.nullit.features_chat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _connectionState = MutableLiveData<Int>()
    // 0 - disconnected
    // 1 - loading
    // 2 - connected

    val connectionState: LiveData<Int>
        get() = _connectionState

    fun connect(chatId: Int) {

    }

    fun disconnect(chatId: Int) {

    }

    fun sendMessage(message: String, chatId: Int) {

    }

}