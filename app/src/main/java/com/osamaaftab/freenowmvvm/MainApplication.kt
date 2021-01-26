package com.osamaaftab.freenowmvvm

import android.app.Application
import com.osamaaftab.freenowmvvm.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    AppModule,
                    ActivityModule,
                    UseCaseModule,
                    NetWorkModule,
                    RepositoryModule,
                    ApiServicesModule
                )
            )
        }
    }

    open fun getApiUrl(): String {
        return "https://fake-poi-api.mytaxi.com"
    }
}