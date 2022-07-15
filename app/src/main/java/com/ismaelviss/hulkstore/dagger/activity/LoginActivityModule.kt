package com.ismaelviss.hulkstore.dagger.activity

import com.ismaelviss.hulkstore.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity
}