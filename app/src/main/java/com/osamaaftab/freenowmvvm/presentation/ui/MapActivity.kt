package com.osamaaftab.freenowmvvm.presentation.ui

import android.location.Address
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.osamaaftab.freenowmvvm.R
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.presentation.viewmodel.MapViewModel
import com.osamaaftab.freenowmvvm.util.AsyncGeocoder
import com.osamaaftab.freenowmvvm.util.Bitmap.bitmapDescriptorFromVector
import com.osamaaftab.freenowmvvm.util.Constant
import org.koin.android.viewmodel.ext.android.viewModel

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    private lateinit var items: ArrayList<VehicleItem>
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        position = intent.getIntExtra(Constant.POSITION, 0)
        items = intent.getParcelableArrayListExtra(Constant.LIST)
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initObserver()
        mapViewModel.onMapReady(items, position)
    }

    private fun initObserver() {
        mapViewModel.getPoolItem().observe(
            this,
            Observer {
                if (::mMap.isInitialized) {

                    AsyncGeocoder(this).reverseGeocode(
                        it.coordinate.latitude, it.coordinate.longitude,
                        object : AsyncGeocoder.Callback {
                            override fun success(address: Address) {
                                addItem(it.coordinate.latitude, it.coordinate.longitude,address.getAddressLine(0),it.fleetType,it.heading.toFloat(),R.drawable.ic_pooling_top)
                            }

                            override fun failure(e: Throwable?) {
                                addItem(it.coordinate.latitude, it.coordinate.longitude,it.coordinate.latitude.toString()+","+it.coordinate.longitude.toString(),it.fleetType,it.heading.toFloat(),R.drawable.ic_pooling_top)
                            }
                        })
                }
            })

        mapViewModel.getTaxiItem().observe(
            this,
            Observer {
                if (::mMap.isInitialized) {
                    AsyncGeocoder(this).reverseGeocode(
                        it.coordinate.latitude, it.coordinate.longitude,
                        object : AsyncGeocoder.Callback {
                            override fun success(address: Address) {
                                addItem(it.coordinate.latitude, it.coordinate.longitude,address.getAddressLine(0),it.fleetType,it.heading.toFloat(),R.drawable.ic_taxi_top)
                            }

                            override fun failure(e: Throwable?) {
                                addItem(it.coordinate.latitude, it.coordinate.longitude,it.coordinate.latitude.toString()+","+it.coordinate.longitude.toString(),it.fleetType,it.heading.toFloat(),R.drawable.ic_taxi_top)
                            }
                        })
                }
            })



        mapViewModel.getSelectedTaxi().observe(
            this,
            Observer {
                if (::mMap.isInitialized) {
                    AsyncGeocoder(this).reverseGeocode(
                        it.coordinate.latitude,it.coordinate.longitude,
                        object : AsyncGeocoder.Callback {
                            override fun success(address: Address) {
                                showSelectedItem(it.coordinate.latitude,it.coordinate.longitude,address.getAddressLine(0),it.fleetType,R.drawable.ic_taxi_top)
                            }

                            override fun failure(e: Throwable?) {
                                showSelectedItem(it.coordinate.latitude,it.coordinate.longitude,it.coordinate.latitude.toString()+","+it.coordinate.longitude.toString(),it.fleetType,R.drawable.ic_taxi_top)
                            }
                        })
                }
            })


        mapViewModel.getSelectedPool().observe(
            this,
            Observer {
                if (::mMap.isInitialized) {
                    AsyncGeocoder(this).reverseGeocode(
                        it.coordinate.latitude,it.coordinate.longitude,
                        object : AsyncGeocoder.Callback {
                            override fun success(address: Address) {
                                showSelectedItem(it.coordinate.latitude,it.coordinate.longitude,address.getAddressLine(0),it.fleetType,R.drawable.ic_pooling_top)
                            }

                            override fun failure(e: Throwable?) {
                                showSelectedItem(it.coordinate.latitude,it.coordinate.longitude,it.coordinate.latitude.toString()+","+it.coordinate.longitude.toString(),it.fleetType,R.drawable.ic_pooling_top)
                            }
                        })
                }
            })
    }

    private fun addItem(
        lat: Double,
        lon: Double,
        address: String,
        title: String,
        heading: Float,
        drawable: Int) {

        val latLng = LatLng(lat, lon)
        var markerOptions: MarkerOptions =
            MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(baseContext, drawable))
                .rotation(heading)
                .title(title)
                .snippet(address)

        mMap.addMarker(markerOptions)

    }
    private fun showSelectedItem(
        lat: Double,
        lon: Double,
        address: String,
        title: String,
        drawable: Int) {

        val marker = MarkerOptions()
        marker.title(title)
        marker.snippet(address)
        marker.icon(bitmapDescriptorFromVector(this, drawable))
        marker.position(LatLng(lat, lon))
        mMap.addMarker(marker).showInfoWindow()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(lat, lon), 16f
            )
        )
    }
}
