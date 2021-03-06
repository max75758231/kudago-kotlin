package com.android.kudago_kotlin.di

import com.android.kudago_kotlin.ui.cities.CitiesDialogFragment
import com.android.kudago_kotlin.ui.details.EventDetailsActivity
import com.android.kudago_kotlin.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ViewModelModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(eventDetailsActivity: EventDetailsActivity)
    fun inject(citiesDialogFragment: CitiesDialogFragment)
}