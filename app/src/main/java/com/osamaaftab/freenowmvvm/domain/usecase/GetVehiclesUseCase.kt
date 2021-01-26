package com.osamaaftab.freenowmvvm.domain.usecase

import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.domain.repository.VehicleRepository
import com.osamaaftab.freenowmvvm.domain.usecase.base.UseCase
import com.osamaaftab.freenowmvvm.util.ApiErrorHandle

class GetVehiclesUseCase constructor(private val newsRepository: VehicleRepository, apiErrorHandle: ApiErrorHandle?) :
    UseCase<List<VehicleItem>, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): List<VehicleItem> {
        return newsRepository.getVehicles()
    }
}