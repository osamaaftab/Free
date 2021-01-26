package com.osamaaftab.freenowmvvm.data.source.remote

import com.osamaaftab.freenowmvvm.domain.model.Vehicles
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface VehicleService {

    @GET("/")
    fun getVehicles(
        @Query("p1Lat") p1Lat: Double,
        @Query("p1Lon") p1Lon: Double,
        @Query("p2Lat") p2Lat: Double,
        @Query("p2Lon") p2Lon: Double
    ): Deferred<Vehicles>

}