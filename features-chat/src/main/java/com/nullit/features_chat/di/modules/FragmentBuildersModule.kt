package com.nullit.features_chat.di.modules

import com.nullit.features_chat.ui.chat.ChatFragment
import com.nullit.features_chat.ui.chatlist.ChatListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun contributeChatListFragment(): ChatListFragment

}