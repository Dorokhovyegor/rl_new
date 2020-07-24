package com.nullit.rtg.di.modules

import com.nullit.rtg.framework.presentation.auth.LoginFragment
import com.nullit.rtg.framework.presentation.auth.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

}