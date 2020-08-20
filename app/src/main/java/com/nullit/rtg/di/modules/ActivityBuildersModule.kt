package com.nullit.rtg.di.modules

import com.nullit.rtg.di.modules.auth.AuthFragmentBuildersModule
import com.nullit.rtg.di.modules.auth.AuthModule
import com.nullit.rtg.di.modules.auth.AuthViewModelModule
import com.nullit.rtg.di.modules.main.MainFragmentBuildersModule
import com.nullit.rtg.di.modules.main.MainModule
import com.nullit.rtg.di.modules.main.MainViewModelModule
import com.nullit.rtg.di.scopes.AuthScope
import com.nullit.rtg.di.scopes.MainScope
import com.nullit.rtg.ui.MainActivity
import com.nullit.rtg.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}