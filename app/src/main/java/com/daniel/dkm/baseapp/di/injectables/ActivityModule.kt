package com.daniel.dkm.baseapp.di.injectables

import androidx.lifecycle.ViewModel
import com.daniel.dkm.baseapp.di.ViewModelKey
import com.daniel.dkm.baseapp.ui.MainActivity
import com.daniel.dkm.baseapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributeSplashActivity(): MainActivity


    @Module
    abstract class SplashActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    }

//    @ContributesAndroidInjector(modules = [MainActivityModule::class])
//    abstract fun contributeMainActivity(): MainActivity
//
//    @Module
//    abstract class MainActivityModule {
//        @Binds
//        @IntoMap
//        @ViewModelKey(MainViewModel::class)
//        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
//    }
}
