package com.nullit.rtg.framework.presentation

import com.nullit.rtg.di.graph.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication: dagger.android.support.DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out dagger.android.support.DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}