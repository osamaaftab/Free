package com.osamaaftab.freenowmvvm.domain.model

import android.os.Parcelable
import com.osamaaftab.freenowmvvm.domain.model.Coordinate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehicleItem(
    val coordinate: Coordinate,
    val fleetType: String,
    val heading: String,
    val id: String
) : Parcelable