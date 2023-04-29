package com.ekenya.rnd.hiltmodule.di.injectables

import androidx.lifecycle.ViewModel
import com.daniel.dkm.baseapp.di.ViewModelKey
import com.ekenya.rnd.hiltmodule.ui.home.HomeFragment
import com.ekenya.rnd.hiltmodule.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TourismFragmentModule {

    @Module
    abstract class TourismHomeFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }

    @ContributesAndroidInjector(modules = [TourismHomeFragmentModule::class])
    abstract fun contributeHomeFragment(): HomeFragment




}
