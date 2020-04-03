package com.android.kudago_kotlin.di

import android.content.Context
import com.android.kudago_kotlin.BuildConfig
import com.android.kudago_kotlin.model.data.server.ApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesAppContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
            okHttpBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

private const val BASE_URL = "https://kudago.com/public-api/v1.4/"