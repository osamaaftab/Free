package com.osamaaftab.freenowmvvm.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.osamaaftab.freenowmvp.presentation.ui.GenericAdapter
import com.osamaaftab.freenowmvvm.R
import com.osamaaftab.freenowmvvm.databinding.ActivityVehicleListBinding
import com.osamaaftab.freenowmvvm.domain.model.VehicleItem
import com.osamaaftab.freenowmvvm.presentation.viewmodel.VehicleListViewModel
import com.osamaaftab.freenowmvvm.util.Constant
import org.koin.android.viewmodel.ext.android.viewModel

class VehicleListActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityVehicleListBinding
    private var position: Int = 0
    private val vehicleViewModel: VehicleListViewModel by viewModel()
    private var vehicleAdapter: GenericAdapter<VehicleItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_vehicle_list)
        activityMainBinding.lifecycleOwner = this
        initAdapter()
        initObserver()
        vehicleViewModel.loadVehicleList()
    }


    private fun initObserver() {

        vehicleViewModel.getNewsList().observe(
            this,
            Observer {
                vehicleAdapter?.itemList = it
                activityMainBinding.vehicleRecyclerView.visibility = View.VISIBLE
            })

        vehicleViewModel.getSelectedPosition().observe(this, Observer {
            position = it
        })
        vehicleViewModel.getOnRedirect().observe(this, Observer { list ->
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra(Constant.POSITION, position)
            intent.putParcelableArrayListExtra(Constant.LIST, ArrayList(list))
            startActivity(intent)
        })

        vehicleViewModel.getOnShow().observe(this, Observer {
            if (it == true) {
                activityMainBinding.indeterminateBar.visibility = View.VISIBLE
            } else activityMainBinding.indeterminateBar.visibility = View.GONE
        })

        vehicleViewModel.getOnError().observe(this, Observer {
            activityMainBinding.statusLbl.visibility = View.VISIBLE
            activityMainBinding.statusLbl.text = it
        })
    }


    private fun initAdapter() {
        vehicleAdapter = object : GenericAdapter<VehicleItem>() {

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return VehicleViewHolder<VehicleItem>(viewDataBinding, vehicleViewModel)
            }

            override fun getLayoutId(): Int {
                return R.layout.vehicle_item
            }
        }
        activityMainBinding.vehicleRecyclerView.adapter = vehicleAdapter
    }
}