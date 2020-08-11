package com.nullit.features_chat.di.modules

import com.nullit.features_chat.ui.ChatActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class]
    )
    abstract fun contributeChatActivity(): ChatActivity

}