package com.osamaaftab.freenowmvvm.domain.repository

import com.osamaaftab.freenowmvvm.domain.model.VehicleItem

interface VehicleRepository {
    suspend fun getVehicles(): List<VehicleItem>
}