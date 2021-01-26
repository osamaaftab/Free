package com.osamaaftab.freenowmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.freenowmvvm.data.repository.VehicleRepositoryImp
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.domain.usecase.GetVehiclesUseCase
import com.osamaaftab.freenowmvvm.util.ApiErrorHandle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetVehicleUseCaseUT {


    lateinit var getVehiclesUseCase: GetVehiclesUseCase

    @MockK
    lateinit var vehicleRepositoryImp: VehicleRepositoryImp

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        val apiErrorHandle = mockk<ApiErrorHandle>()
        getVehiclesUseCase = GetVehiclesUseCase(vehicleRepositoryImp, apiErrorHandle)
    }


    @Test
    fun getVehicleData() = runBlocking {
        val vehicles = mockk<List<VehicleItem>>()
        every { runBlocking { vehicleRepositoryImp.getVehicles() } } returns (vehicles)
        val expectedPost = vehicleRepositoryImp.getVehicles()
        every { runBlocking { getVehiclesUseCase.run() } } returns (vehicles)
        val expectedResult = getVehiclesUseCase.run()
        assertEquals(expectedPost, expectedResult)
    }
}