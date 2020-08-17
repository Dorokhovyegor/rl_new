package com.nullit.rtg.di.modules

import com.nullit.features_chat.ui.chat.ChatFragment
import com.nullit.features_chat.ui.chatlist.ChatListFragment
import com.nullit.rtg.ui.auth.LoginFragment
import com.nullit.rtg.ui.auth.RegistrationFragment
import com.nullit.rtg.ui.auth.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun contributeChatListFragment(): ChatListFragment

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

}