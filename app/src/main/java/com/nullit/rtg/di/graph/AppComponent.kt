package com.nullit.rtg.di.graph

import android.app.Application
import com.nullit.rtg.di.modules.ActivityBuildersModule
import com.nullit.rtg.di.modules.AppModule
import com.nullit.rtg.di.modules.ViewModelFactoryModule
import com.nullit.rtg.ui.CustomApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<CustomApplication> {

    fun application(): Application

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}