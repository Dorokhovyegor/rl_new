package com.nullit.rtg.di.modules

import com.nullit.rtg.framework.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}