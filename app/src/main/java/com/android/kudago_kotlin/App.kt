package com.android.kudago_kotlin

import android.app.Application
import com.android.kudago_kotlin.di.AppComponent
import com.android.kudago_kotlin.di.AppModule
import com.android.kudago_kotlin.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        Stetho.initializeWithDefaults(this)
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        )
    }
}