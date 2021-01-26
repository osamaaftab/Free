package com.osamaaftab.freenowmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.domain.usecase.GetVehiclesUseCase
import com.osamaaftab.freenowmvvm.presentation.viewmodel.VehicleListViewModel
import com.osamaaftab.freenowmvvm.util.ApiErrorHandle
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetVehicleListViewModelUT {


    @RelaxedMockK
    lateinit var getNewsUseCase: GetVehiclesUseCase

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var newsViewModel: VehicleListViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        newsViewModel = VehicleListViewModel(getNewsUseCase)
    }


    @Test
    fun onItemClick() {
        val vehicles = mockk<List<VehicleItem>>()
        newsViewModel.onItemClick(vehicles, 0)
        val expectedPosition = newsViewModel.getSelectedPosition().value
        Assert.assertEquals(0, expectedPosition)
        val expectedList = newsViewModel.getOnRedirect().value
        Assert.assertEquals(vehicles, expectedList)
    }

    @Test
    fun onSuccess() {
        val vehicles = mockk<List<VehicleItem>>()
        newsViewModel.getVehicleUseCaseResponse().onSuccess(vehicles)
        val response = newsViewModel.getNewsList().value
        val state = newsViewModel.getOnShow().value
        Assert.assertEquals(false, state)
        Assert.assertEquals(vehicles, response)
    }

    @Test
    fun onFails() {
        val apiErrorHandle = ApiErrorHandle()
        val throwable = mockk<Throwable>()
        apiErrorHandle.traceErrorException(throwable)
        newsViewModel.getVehicleUseCaseResponse()
            .onError(apiErrorHandle.traceErrorException(throwable))
        val state = newsViewModel.getOnShow().value
        val msg = newsViewModel.getOnError().value
        Assert.assertEquals("Bad response!", msg)
        Assert.assertEquals(false, state)
    }

    @Test
    fun onLoad() {
        newsViewModel.loadVehicleList()
        val state = newsViewModel.getOnShow().value
        Assert.assertEquals(true, state)
    }
}