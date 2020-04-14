package com.android.kudago_kotlin.di

import com.android.kudago_kotlin.db.DatabaseService
import com.android.kudago_kotlin.db.DatabaseServiceImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDatabaseService(): DatabaseService = DatabaseServiceImpl()
}