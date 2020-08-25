package com.nullit.rtg.di.modules.main.profile

import androidx.lifecycle.ViewModel
import com.nullit.features_profile.ui.profile.ProfileViewModel
import com.nullit.rtg.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel
}