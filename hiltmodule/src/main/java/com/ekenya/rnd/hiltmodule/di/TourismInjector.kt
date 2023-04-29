package com.ekenya.rnd.hiltmodule.di

import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.baseapp.di.BaseModuleInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

@Keep
class TourismInjector: BaseModuleInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>


    override fun inject(app: TopApp) {
        DaggerTourismComponent.builder()
            .appComponent(app.appComponent)
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Any> {
        return activityInjector
    }
}
