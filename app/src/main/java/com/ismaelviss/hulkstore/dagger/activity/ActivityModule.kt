package com.ismaelviss.hulkstore.dagger.activity

import dagger.Module

@Module(
    includes = [
        LoginActivityModule::class,
        StoreCartActivityModule::class
    ]
)
class ActivityModule {
}