package com.ismaelviss.hulkstore.dagger

import android.app.Application
import com.ismaelviss.hulkstore.ApplicationHulkStore
import com.ismaelviss.hulkstore.dagger.activity.ActivityModule
import com.ismaelviss.hulkstore.dagger.module.RepositoryModule
import com.ismaelviss.hulkstore.dagger.module.ServiceModule
import com.ismaelviss.hulkstore.dagger.module.UrlModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ServiceModule::class,
        ActivityModule::class,
        UrlModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

    fun inject(applicationFzSports: ApplicationHulkStore)
}