package com.enderx.jobsearcher.feature

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.enderx.jobsearcher.core.BaseFragment
import com.enderx.jobsearcher.core.BaseViewModel

abstract class JobFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseFragment<VM, DB>() {
    protected val sharedViewModel: JobViewModel by activityViewModels()
}