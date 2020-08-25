package com.nullit.rtg.di.modules.main

import com.nullit.features_chat.ui.chat.ChatFragment
import com.nullit.features_chat.ui.chatlist.ChatListFragment
import com.nullit.features_profile.ui.profile.EditProfileInfoFragment
import com.nullit.features_profile.ui.profile.ProfileFragment
import com.nullit.rtg.di.modules.main.chat.ChatModule
import com.nullit.rtg.di.modules.main.chat.ChatViewModelModule
import com.nullit.rtg.di.modules.main.profile.ProfileModule
import com.nullit.rtg.di.modules.main.profile.ProfileViewModelModule
import com.nullit.rtg.di.scopes.ChatFeatureScope
import com.nullit.rtg.di.scopes.ProfileFeatureScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ChatFeatureScope
    @ContributesAndroidInjector(modules = [ChatViewModelModule::class, ChatModule::class])
    abstract fun contributeChatFragment(): ChatFragment

    @ChatFeatureScope
    @ContributesAndroidInjector(modules = [ChatViewModelModule::class, ChatModule::class])
    abstract fun contributeChatListFragment(): ChatListFragment

    @ProfileFeatureScope
    @ContributesAndroidInjector(modules = [ProfileViewModelModule::class, ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @ProfileFeatureScope
    @ContributesAndroidInjector(modules = [ProfileViewModelModule::class, ProfileModule::class])
    abstract fun contributeEditUserInfoFragment(): EditProfileInfoFragment

}