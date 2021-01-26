package com.osamaaftab.freenowmvvm.data.repository

import com.osamaaftab.freenowmvvm.domain.repository.VehicleRepository
import com.osamaaftab.freenowmvvm.data.source.remote.VehicleService
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem

class VehicleRepositoryImp (private val vehicleServices: VehicleService) :

    VehicleRepository {
    override suspend fun getVehicles(): List<VehicleItem> {
        return vehicleServices.getVehicles(53.694865, 9.757589, 53.394655, 10.099891).await().poiList
    }
}