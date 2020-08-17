package com.nullit.rtg.di.modules

import com.nullit.rtg.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class, ViewModelFactoryModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}