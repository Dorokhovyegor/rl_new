package com.nullit.rtg.di.modules

import com.nullit.features_chat.di.modules.ChatModule
import com.nullit.features_chat.di.modules.ViewModelFactoryModule
import com.nullit.features_chat.ui.ChatActivity
import com.nullit.rtg.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [ChatModule::class, com.nullit.features_chat.di.modules.FragmentBuildersModule::class, com.nullit.features_chat.di.modules.ViewModelModule::class, ViewModelFactoryModule::class]
    )
    abstract fun contributeChatActivity(): ChatActivity
}