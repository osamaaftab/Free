package com.osamaaftab.freenowmvvm

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.osamaaftab.freenowmvvm.presentation.ui.VehicleListActivity
import com.osamaaftab.freenowmvvm.util.SuccessDispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class VehicleListActivityTest : KoinTest {

    @JvmField
    @Rule
    var activityTestRule = ActivityTestRule(VehicleListActivity::class.java, true, false)

    private var mockWebServer = MockWebServer()
    private val client: OkHttpClient by inject()


    @Before
    fun setup() {
        mockWebServer.start(4007)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                client
            )
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun onFetchVehicleListSuccess() {
        mockWebServer.dispatcher = SuccessDispatcher()
        activityTestRule.launchActivity(null)
        Espresso.onView(withId(R.id.indeterminateBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(withId(R.id.vehicleRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun onFetchVehicleListFails() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
        activityTestRule.launchActivity(null)
        Espresso.onView(withId(R.id.indeterminateBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(withId(R.id.statusLbl))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}