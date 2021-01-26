package com.osamaaftab.freenowmvvm.di.module

import com.osamaaftab.freenowmvvm.domain.repository.VehicleRepository
import com.osamaaftab.freenowmvvm.domain.usecase.GetVehiclesUseCase
import com.osamaaftab.freenowmvvm.util.ApiErrorHandle
import org.koin.dsl.module

val UseCaseModule = module {
    single { provideGetNewsUseCase(get(), provideApiError()) }
}

fun provideGetNewsUseCase(
    newsRepository: VehicleRepository,
    apiErrorHandle: ApiErrorHandle
): GetVehiclesUseCase {
    return GetVehiclesUseCase(newsRepository, apiErrorHandle)
}