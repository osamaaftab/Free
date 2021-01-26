package com.osamaaftab.freenowmvvm.di.module

import com.osamaaftab.freenowmvvm.presentation.viewmodel.MapViewModel
import com.osamaaftab.freenowmvvm.presentation.viewmodel.VehicleListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ActivityModule = module {
    viewModel { VehicleListViewModel(get()) }
    viewModel { MapViewModel() }
}