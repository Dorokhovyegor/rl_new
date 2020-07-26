package com.nullit.rtg.di.modules

import androidx.lifecycle.ViewModel
import com.nullit.rtg.di.ViewModelKey
import com.nullit.rtg.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}