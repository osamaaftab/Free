package com.osamaaftab.freenowmvvm.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(val latitude: Double, val longitude: Double) : Parcelable