package com.nullit.features_chat.di.modules

import androidx.lifecycle.ViewModel
import com.nullit.features_chat.di.ViewModelKey
import com.nullit.features_chat.ui.chat.ChatViewModel
import com.nullit.features_chat.ui.chatlist.ChatListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(chatViewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    abstract fun bindChatListViewModel(chatViewModel: ChatListViewModel): ViewModel


}