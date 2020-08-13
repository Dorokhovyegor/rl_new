package com.nullit.features_chat.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nullit.features_chat.ui.chat.ChatViewModel
import com.nullit.features_chat.utils.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

val ARG_CHAT = "chatId"

abstract class BaseChatFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    protected lateinit var chatViewModel: ChatViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewModel = ViewModelProvider(this, viewModelProviderFactory)[ChatViewModel::class.java]
    }
}