package com.daniel.dkm.hiltmodule.di.injectables

import androidx.lifecycle.ViewModel
import com.daniel.dkm.baseapp.di.ViewModelKey
import com.daniel.dkm.hiltmodule.MainActivity
import com.daniel.dkm.hiltmodule.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TourismActivityModule {

    /**
     * Main Activity
     */
    ///////////////////////////////////////////////////////////////////////////////////
    @ContributesAndroidInjector(modules = [TourismMainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @Module
    abstract class TourismMainActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindLoginViewModel(viewModel: HomeViewModel): ViewModel
    }

}
