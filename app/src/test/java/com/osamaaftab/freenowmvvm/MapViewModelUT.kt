package com.osamaaftab.freenowmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.freenowmvvm.domain.model.Coordinate
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.presentation.viewmodel.MapViewModel
import io.mockk.MockKAnnotations
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelUT {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mapViewModel: MapViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        mapViewModel = MapViewModel()
    }

    @Test
    fun onAddPoolItem() {
        val vehicles = creatPoolResponse()
        mapViewModel.onMapReady(vehicles, 0)
        val expectedPosition = mapViewModel.getPoolItem().value
        Assert.assertEquals("POOLING", expectedPosition!!.fleetType)
    }

    @Test
    fun onAddTaxiItem() {
        val vehicles = createTaxiResponse()
        mapViewModel.onMapReady(vehicles, 0)
        val expectedPosition = mapViewModel.getTaxiItem().value
        Assert.assertEquals("TAXI", expectedPosition!!.fleetType)
    }

    @Test
    fun onAddSelectedTaxiItem() {
        val vehicles = createTaxiResponse()
        mapViewModel.onMapReady(vehicles, 0)
        val expectedPosition = mapViewModel.getSelectedTaxi().value
        Assert.assertEquals("TAXI", expectedPosition!!.fleetType)
    }

    @Test
    fun onAddSelectedPoolItem() {
        val vehicles = creatPoolResponse()
        mapViewModel.onMapReady(vehicles, 0)
        val expectedPosition = mapViewModel.getSelectedPool().value
        Assert.assertEquals("POOLING", expectedPosition!!.fleetType)
    }


    private fun creatPoolResponse(): List<VehicleItem> {
        val item = VehicleItem(
            id = "845157",
            coordinate = Coordinate(53.45946960311767, 10.01735414754243),
            fleetType = "POOLING",
            heading = "122.79980047690044"
        )
        val news = mutableListOf<VehicleItem>()
        for (x in 0..10) {
            news.add(item)
        }
        return news
    }

    private fun createTaxiResponse(): List<VehicleItem> {
        val item = VehicleItem(
            id = "845157",
            coordinate = Coordinate(53.45946960311767, 10.01735414754243),
            fleetType = "TAXI",
            heading = "122.79980047690044"
        )
        val news = mutableListOf<VehicleItem>()
        for (x in 0..10) {
            news.add(item)
        }
        return news
    }

}