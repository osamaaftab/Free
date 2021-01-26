package com.osamaaftab.freenowmvvm.presentation.ui

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.osamaaftab.freenowmvp.presentation.ui.GenericAdapter
import com.osamaaftab.freenowmvvm.presentation.viewmodel.VehicleListViewModel

class VehicleViewHolder<T>(
    private val viewDataBinding: ViewDataBinding,
    private val viewModel: VehicleListViewModel?
) : RecyclerView.ViewHolder(viewDataBinding.root),
    GenericAdapter.Binder<T> {


    override fun bind(data: T, position: Int) {
        viewDataBinding.setVariable(BR.vehicle, data)
        viewDataBinding.setVariable(BR.position,position)
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.executePendingBindings()
    }

    override fun bindList(data: List<T>) {
        viewDataBinding.setVariable(BR.list, data)
    }
}