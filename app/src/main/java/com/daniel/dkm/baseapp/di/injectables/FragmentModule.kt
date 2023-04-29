package com.daniel.dkm.baseapp.di.injectables

import androidx.lifecycle.ViewModel
import com.daniel.dkm.baseapp.di.ViewModelKey
import com.daniel.dkm.baseapp.ui.main.MainFragment
import com.daniel.dkm.baseapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment


    @Module
    abstract class MainFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
    }
}