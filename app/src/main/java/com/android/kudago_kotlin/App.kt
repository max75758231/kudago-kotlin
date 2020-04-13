package com.android.kudago_kotlin

import androidx.multidex.MultiDexApplication
import com.android.kudago_kotlin.di.AppComponent
import com.android.kudago_kotlin.di.AppModule
import com.android.kudago_kotlin.di.DaggerAppComponent
import com.facebook.stetho.Stetho


class App : MultiDexApplication() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        Stetho.initializeWithDefaults(this)
    }
}