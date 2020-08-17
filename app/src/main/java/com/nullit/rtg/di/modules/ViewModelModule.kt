package com.nullit.rtg.di.modules

import androidx.lifecycle.ViewModel
import com.nullit.features_chat.ui.chat.ChatViewModel
import com.nullit.features_chat.ui.chatlist.ChatListViewModel
import com.nullit.rtg.di.ViewModelKey
import com.nullit.rtg.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(chatViewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    abstract fun bindChatListViewModel(chatViewModel: ChatListViewModel): ViewModel

}