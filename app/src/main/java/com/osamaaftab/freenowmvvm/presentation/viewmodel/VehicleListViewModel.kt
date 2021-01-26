package com.osamaaftab.freenowmvvm.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.osamaaftab.freenowmvvm.domain.model.ErrorModel
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.domain.usecase.GetVehiclesUseCase
import com.osamaaftab.freenowmvvm.domain.usecase.base.UseCaseResponse
import com.osamaaftab.freenowmvvm.presentation.base.BaseViewModel
import com.osamaaftab.freenowmvvm.util.SingleLiveEvent

class VehicleListViewModel constructor(private val getVehiclesUseCase: GetVehiclesUseCase) :
    BaseViewModel() {


    private val onShow = MutableLiveData<Boolean>()
    private val onError = MutableLiveData<String>()
    private val vehicleList = MutableLiveData<List<VehicleItem>>()
    private val onRedirect = SingleLiveEvent<List<VehicleItem>>()
    private val selectedPosition = MutableLiveData<Int>()


    fun getVehicleUseCaseResponse() = object : UseCaseResponse<List<VehicleItem>> {
        override fun onSuccess(result: List<VehicleItem>) {
            onShow.value = false
            Log.i(ContentValues.TAG, "result: $result")
            vehicleList.value = result
        }

        override fun onError(errorModel: ErrorModel?) {
            Log.i(ContentValues.TAG, "error: $errorModel?.message code")
            onShow.value = false
            onError.value = errorModel?.getErrorMessage().toString()
        }
    }

    fun getOnError(): LiveData<String> {
        return onError
    }

    fun getSelectedPosition(): LiveData<Int> {
        return selectedPosition
    }

    fun getOnRedirect(): SingleLiveEvent<List<VehicleItem>> {
        return onRedirect
    }

    fun getOnShow(): LiveData<Boolean> {
        return onShow
    }

    fun getNewsList(): LiveData<List<VehicleItem>> {
        return vehicleList
    }

    fun onItemClick(list: List<VehicleItem>, position: Int) {
        selectedPosition.value = position
        onRedirect.value = list
    }

    fun loadVehicleList() {
        if (vehicleList.value == null) {
            onShow.value = true

            getVehiclesUseCase.invoke(scope, null, getVehicleUseCaseResponse())

        }
    }
}