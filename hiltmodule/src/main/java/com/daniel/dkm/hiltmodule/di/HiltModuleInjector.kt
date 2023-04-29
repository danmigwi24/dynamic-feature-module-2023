package com.daniel.dkm.hiltmodule.di

import androidx.annotation.Keep
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.baseapp.di.BaseModuleInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

@Keep
class HiltModuleInjector: BaseModuleInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>


    override fun inject(app: TopApp) {
        DaggerHiltModuleComponent.builder()
            .appComponent(app.appComponent)
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Any> {
        return activityInjector
    }
}
