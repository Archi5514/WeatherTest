package com.example.weathertest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weathertest.launchWhenStarted
import com.example.weathertest.model.entity.AppStateEntity
import com.example.weathertest.viewmodel.BaseViewModel

abstract class BaseFragment<VB : ViewDataBinding, D : AppStateEntity, VM : BaseViewModel<D>>(
    private val layoutID: Int
) : Fragment() {

    abstract val viewModel: VM
    private var bindingNullable: VB? = null
    protected val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<VB>(
        inflater,
        layoutID,
        container,
        false
    ).apply { bindingNullable = this }
        .root

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateLiveData.launchWhenStarted(lifecycleScope) { state ->
            renderData(state)
        }

        viewModel.onViewInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

    protected open fun renderData(state: AppState<D>) {
        when (state) {
            is AppState.Success -> renderSuccess(state.data)
            is AppState.Error -> renderError(state.error)
        }
    }

    protected open fun renderSuccess(data: D) {}

    protected open fun renderError(error: Throwable) {
        error.message?.let { Log.d("ERROR", it) }
    }

}