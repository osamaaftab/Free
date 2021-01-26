package com.osamaaftab.freenowmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.presentation.base.BaseViewModel

class MapViewModel : BaseViewModel() {

    private val PoolItem = MutableLiveData<VehicleItem>()
    private val TaxiItem = MutableLiveData<VehicleItem>()
    private val SelectedTaxi = MutableLiveData<VehicleItem>()
    private val SelectedPool = MutableLiveData<VehicleItem>()


    fun onMapReady(items: List<VehicleItem>, position: Int) {

        if (items[position].fleetType.contentEquals("TAXI")) {
            SelectedTaxi.value = items[position]
        } else {
            SelectedPool.value = items[position]
        }

        for (i in items.indices) {
            if (items[i].fleetType.contentEquals("TAXI")) {
                TaxiItem.value = items[i]
            } else {
                PoolItem.value = items[i]
            }
        }
    }


    fun getPoolItem(): LiveData<VehicleItem> {
        return PoolItem
    }

    fun getSelectedTaxi(): LiveData<VehicleItem> {
        return SelectedTaxi
    }

    fun getSelectedPool(): LiveData<VehicleItem> {
        return SelectedPool
    }

    fun getTaxiItem(): LiveData<VehicleItem> {
        return TaxiItem
    }

}