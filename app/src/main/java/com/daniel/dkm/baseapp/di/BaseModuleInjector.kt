package com.daniel.dkm.baseapp.di

import com.daniel.dkm.baseapp.TopApp
import dagger.android.DispatchingAndroidInjector

interface BaseModuleInjector {

    fun inject(app: TopApp)

    fun activityInjector(): DispatchingAndroidInjector<Any>
}
