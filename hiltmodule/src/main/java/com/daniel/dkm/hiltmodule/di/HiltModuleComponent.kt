package com.daniel.dkm.hiltmodule.di

import com.daniel.dkm.baseapp.di.AppComponent
import com.daniel.dkm.baseapp.di.ModuleScope
import com.daniel.dkm.baseapp.di.injectables.ViewModelModule
import com.daniel.dkm.hiltmodule.di.injectables.TourismActivityModule
import com.daniel.dkm.hiltmodule.di.injectables.TourismFragmentModule
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
interface HiltModuleComponent {
    fun inject(injector: HiltModuleInjector)
}