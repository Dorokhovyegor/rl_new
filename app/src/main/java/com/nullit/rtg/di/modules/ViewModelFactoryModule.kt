package com.nullit.rtg.di.modules

import androidx.lifecycle.ViewModelProvider
import com.nullit.rtg.framework.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}