package com.android.kudago_kotlin

import android.app.Application
import com.android.kudago_kotlin.di.AppComponent
import com.android.kudago_kotlin.di.DaggerAppComponent
import com.facebook.stetho.Stetho


class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}