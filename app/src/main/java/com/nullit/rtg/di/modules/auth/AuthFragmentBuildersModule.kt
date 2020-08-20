package com.nullit.rtg.di.modules.auth

import com.nullit.rtg.ui.auth.LoginFragment
import com.nullit.rtg.ui.auth.RegistrationFragment
import com.nullit.rtg.ui.auth.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment


}