package com.nullit.features_chat.di.modules

import androidx.lifecycle.ViewModelProvider
import com.nullit.features_chat.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}