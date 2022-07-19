package com.enderx.jobsearcher.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.navigation.fragment.findNavController
import com.enderx.jobsearcher.core.component.loading.LoadingType
import com.enderx.jobsearcher.core.component.toolbar.ToolbarData
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    private var binding: DB? = null

    @Suppress("LeakingThis")
    protected val viewModel by createViewModelLazy(getViewModelClass(), { this.viewModelStore })

    protected val dataBinding: DB
        get() = binding!!

    abstract fun getViewModelClass(): KClass<VM>

    @LayoutRes
    protected abstract fun getContentLayoutId(): Int

    protected abstract fun onBindView()

    protected open fun getToolbarData(): ToolbarData = ToolbarData.TOOLBAR_NO_DATA

    protected fun updateToolBar() {
        viewModel.updateToolbar(getToolbarData()) {
            onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel.observeLoading(this, this::handleLoading)
        onBindView()
        updateToolBar()
        return dataBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun handleLoading(pair: Pair<LoadingType, Boolean>) {
        if (pair.second) {
            startLoading(pair.first)
        } else {
            stopLoading()
        }
    }

    protected fun startLoading(loadingType: LoadingType) {
        (requireActivity() as BaseActivity).startLoading(loadingType)
    }

    protected fun stopLoading() {
        (requireActivity() as BaseActivity).stopLoading()
    }

    open fun onBackPressed() {
        if (!(requireActivity() as BaseActivity).isLoading && !findNavController().popBackStack()) {
            requireActivity().finish()
        }
    }
}



