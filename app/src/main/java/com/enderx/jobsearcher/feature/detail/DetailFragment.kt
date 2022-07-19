package com.enderx.jobsearcher.feature.detail

import android.os.Bundle
import android.view.View
import com.enderx.jobsearcher.R
import com.enderx.jobsearcher.core.component.toolbar.ToolbarData
import com.enderx.jobsearcher.databinding.FragmentDetailBinding
import com.enderx.jobsearcher.feature.JobFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class DetailFragment : JobFragment<DetailViewModel, FragmentDetailBinding>() {
    override fun getViewModelClass(): KClass<DetailViewModel> = DetailViewModel::class

    override fun getContentLayoutId(): Int = R.layout.fragment_detail

    override fun getToolbarData(): ToolbarData = ToolbarData(
        title = getString(R.string.detail)
    )

    override fun onBindView() {
        with(dataBinding) {
            viewModel = this@DetailFragment.viewModel
            sharedViewModel = this@DetailFragment.sharedViewModel
            executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}