package com.nullit.rtg.di.modules.main

import androidx.lifecycle.ViewModel
import com.nullit.features_chat.ui.chat.ChatViewModel
import com.nullit.features_chat.ui.chatlist.ChatListViewModel
import com.nullit.rtg.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(chatViewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    abstract fun bindChatListViewModel(chatViewModel: ChatListViewModel): ViewModel

}