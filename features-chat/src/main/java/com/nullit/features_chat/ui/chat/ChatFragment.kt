package com.nullit.features_chat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.ARG_CHAT
import com.nullit.features_chat.ui.BaseChatFragment
import com.nullit.features_chat.ui.adapters.MessageRecyclerViewAdapter
import com.nullit.core.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val ARH_CHAT = "chatId"

class ChatFragment : BaseChatFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var chatViewModel: ChatViewModel
    protected lateinit var messageAdapter: MessageRecyclerViewAdapter

    var chatId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getInt(ARG_CHAT) == null) {
            findNavController().popBackStack()
        }
        chatId = arguments?.getInt(ARG_CHAT)

        chatViewModel = ViewModelProvider(this, viewModelProviderFactory)[ChatViewModel::class.java]
        toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        editTextLayout.setEndIconOnClickListener {
            chatId?.let { it1 ->
                chatViewModel.sendMessage(editText.text.toString(), it1)
                editText.setText("")
            }
        }
        initRecyclerView()
        connect(chatId!!)
        subscribeObserver()
    }

    private fun initRecyclerView() {
        msgRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            messageAdapter = MessageRecyclerViewAdapter()
            adapter = messageAdapter
        }
    }

    private fun subscribeObserver() {
        chatViewModel.socketConnectionState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                SUCCESS_STATE -> {
                    toolBar.title = "Чат"
                    lifecycleScope.launchWhenStarted {
                        chatViewModel.messageFlow().collect {
                            messageAdapter.appendToList(it)
                            msgRecyclerView.smoothScrollToPosition(0)
                        }
                    }
                }
                ERROR_STATE -> toolBar.title = "Ошибка"
                LOADING_STATE -> toolBar.title = "Подключение..."
            }
        })

        chatViewModel.progressBar.observe(viewLifecycleOwner, Observer { loading ->
            progressCircular.visibility = if (loading) View.VISIBLE else View.GONE
        })

        chatViewModel.snackbar.observe(viewLifecycleOwner, Observer { snackBarMessage ->
            view?.let {
                Snackbar.make(it, snackBarMessage.toString(), Snackbar.LENGTH_LONG).show()
            }
            chatViewModel.onSnackBarShown()
        })
    }

    override fun observeSessionState() {
        chatViewModel.endSession.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToAuthActivity()
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