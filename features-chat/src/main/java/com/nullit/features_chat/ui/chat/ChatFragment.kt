package com.nullit.features_chat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.ARG_CHAT
import com.nullit.features_chat.ui.BaseChatFragment
import kotlinx.android.synthetic.main.fragment_chat.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARH_CHAT = "chatId"

class ChatFragment : BaseChatFragment() {

    protected var chatId: Int? = 1

    override fun onStart() {
        super.onStart()
        if (arguments?.getInt(ARG_CHAT) == null) {
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
        subscribeObserver()
    }

    private fun subscribeObserver() {
        chatViewModel.socketConnectionState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                SUCCESS_STATE -> stateTextView.text = "Подключено"
                ERROR_STATE -> stateTextView.text = "Ошибка"
                LOADING_STATE -> stateTextView.text = "Подключение..."
            }
        })

        chatViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            progressCircular.visibility = if (loading) View.VISIBLE else View.GONE
        })

        chatViewModel.snackBar.observe(viewLifecycleOwner, Observer { snackBarMessage ->
            view?.let {
                Snackbar.make(it, snackBarMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun connect(chatId: Int) {
        chatViewModel.connect(chatId)
    }

    private fun disconnect() {
        chatViewModel.disconnect()
    }

    override fun onDetach() {
        super.onDetach()
        disconnect()
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