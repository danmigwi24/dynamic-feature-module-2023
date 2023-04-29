package com.ekenya.rnd.hiltmodule.di

import com.daniel.dkm.baseapp.di.AppComponent
import com.daniel.dkm.baseapp.di.ModuleScope
import com.daniel.dkm.baseapp.di.injectables.ViewModelModule
import com.ekenya.rnd.hiltmodule.di.injectables.TourismActivityModule
import com.ekenya.rnd.hiltmodule.di.injectables.TourismFragmentModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ModuleScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        TourismActivityModule::class,
        TourismFragmentModule::class,
        ViewModelModule::class
    ]
)
interface TourismComponent {
    fun inject(injector: TourismInjector)
}