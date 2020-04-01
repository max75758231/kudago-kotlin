package com.android.kudago_kotlin.di

import androidx.lifecycle.ViewModel
import com.android.kudago_kotlin.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(mainViewModel: MainViewModel): ViewModel
}