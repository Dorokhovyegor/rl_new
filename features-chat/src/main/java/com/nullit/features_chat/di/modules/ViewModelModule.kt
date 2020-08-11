package com.nullit.features_chat.di.modules

import androidx.lifecycle.ViewModel
import com.nullit.features_chat.di.ViewModelKey
import com.nullit.features_chat.ui.chat.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(chatViewModel: ChatViewModel): ViewModel

}