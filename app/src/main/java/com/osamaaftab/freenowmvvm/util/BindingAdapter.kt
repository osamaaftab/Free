package com.osamaaftab.freenowmvvm.util

import android.location.Address
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.osamaaftab.freenowmvvm.R

@BindingAdapter("fleetType")
fun ImageView.setImageUrl(value: String) {
    if (value.contentEquals("TAXI")) {
        this.setImageResource(R.drawable.ic_taxi)
    } else {
        this.setImageResource(R.drawable.ic_pool)
    }
}

@BindingAdapter("latitude", "longitude")
fun TextView.setGeoCoder(latitude: Double, longitude: Double) {
    this@setGeoCoder.text = "$latitude,$longitude"

}