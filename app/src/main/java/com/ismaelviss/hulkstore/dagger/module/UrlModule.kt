package com.ismaelviss.hulkstore.dagger.module

import com.ismaelviss.hulkstore.configurations.OkHttpClientConfig.Companion.URL
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UrlModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return URL
    }
}