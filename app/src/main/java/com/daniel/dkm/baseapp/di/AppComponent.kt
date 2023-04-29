package com.daniel.dkm.baseapp.di

import android.app.Application
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.baseapp.di.injectables.ActivityModule
import com.daniel.dkm.baseapp.di.injectables.FragmentModule
import com.daniel.dkm.baseapp.di.injectables.ViewModelModule
import com.daniel.dkm.common.data.repository.SampleRepository
import com.daniel.dkm.common.workmanger.di_workmanager.WorkerBindingModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class,
        WorkerBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<TopApp?> {
    fun sampleRepository(): SampleRepository
    val app: Application

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TopApp?>() {
        abstract override fun build(): AppComponent
    }
}
