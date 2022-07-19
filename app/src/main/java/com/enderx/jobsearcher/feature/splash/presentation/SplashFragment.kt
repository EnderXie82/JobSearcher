package com.enderx.jobsearcher.feature.splash.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.enderx.jobsearcher.R
import com.enderx.jobsearcher.core.BaseFragment
import com.enderx.jobsearcher.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    override fun getViewModelClass(): KClass<SplashViewModel> = SplashViewModel::class

    override fun getContentLayoutId(): Int = R.layout.fragment_splash

    override fun onBindView() {
        with(dataBinding) {
            viewModel = this@SplashFragment.viewModel
            executePendingBindings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setFirstLaunched(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            findNavController().popBackStack()
        }, 3000)
    }
}