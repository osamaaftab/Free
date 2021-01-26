package com.osamaaftab.freenowmvvm.di.module

import com.osamaaftab.freenowmvvm.data.source.remote.VehicleService
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiServicesModule = module {
    single { provideUserService(get()) }
}

fun provideUserService(retrofit: Retrofit): VehicleService {
    return retrofit.create(VehicleService::class.java)
}