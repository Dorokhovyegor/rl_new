package com.nullit.rtg.di.modules.main

import com.nullit.features_chat.ui.chat.ChatFragment
import com.nullit.features_chat.ui.chatlist.ChatListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun contributeChatListFragment(): ChatListFragment

}