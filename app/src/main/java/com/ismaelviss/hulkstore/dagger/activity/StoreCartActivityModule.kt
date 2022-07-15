package com.ismaelviss.hulkstore.dagger.activity

import com.ismaelviss.hulkstore.ui.store.DialogPaymentSuccessFragment
import com.ismaelviss.hulkstore.ui.store.StoreCartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StoreCartActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeStoreCartActivity(): StoreCartActivity

    @ContributesAndroidInjector
    abstract fun contributeDialogPaymentSuccessFragment(): DialogPaymentSuccessFragment
}