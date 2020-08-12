package com.nullit.features_chat.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.nullit.features_chat.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARH_CHAT = "chatId"

class ChatFragment : BaseChatFragment() {

    protected var chatId: Int? = 1

    override fun onStart() {
        super.onStart()
        if (arguments?.getInt(ARG_CHAT) == null) {
            Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        chatId = arguments?.getInt(ARG_CHAT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connect(chatId!!)
    }

    private fun connect(chatId: Int) {
        chatViewModel.connect(chatId)
    }

    private fun disconnect(chatId: Int) {
        chatViewModel.disconnect(chatId)
    }

    override fun onDetach() {
        super.onDetach()
        disconnect(chatId!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(chatId: Int) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARH_CHAT, chatId)
                }
            }
    }
}