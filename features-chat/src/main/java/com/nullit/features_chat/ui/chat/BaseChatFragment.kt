package com.nullit.features_chat.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

val ARG_CHAT = "chatId"

abstract class BaseChatFragment : Fragment() {

    protected lateinit var chatViewModel: ChatViewModel
    protected var chatId: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.getInt(ARG_CHAT) == null) {
            Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        chatId = arguments?.getInt(ARG_CHAT)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        connect(chatId!!)
    }

    override fun onDetach() {
        super.onDetach()
        disconnect()
    }

    protected fun connect(chatId: Int) {
        chatViewModel.connect(chatId)
    }

    protected fun disconnect() {
        chatId?.let {
            chatViewModel.disconnect(it)
        }
    }
}