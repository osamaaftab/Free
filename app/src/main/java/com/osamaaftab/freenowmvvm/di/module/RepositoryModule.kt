package com.osamaaftab.freenowmvvm.di.module

import com.osamaaftab.freenowmvvm.data.repository.VehicleRepositoryImp
import com.osamaaftab.freenowmvvm.data.source.remote.VehicleService
import com.osamaaftab.freenowmvvm.domain.repository.VehicleRepository
import org.koin.dsl.module

val RepositoryModule = module {

    single { provideNewsRepository(get()) }
}

fun provideNewsRepository(newsService: VehicleService): VehicleRepository {
    return VehicleRepositoryImp(newsService)
}