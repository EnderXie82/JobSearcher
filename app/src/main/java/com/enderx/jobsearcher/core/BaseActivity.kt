package com.enderx.jobsearcher.core

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.enderx.jobsearcher.core.component.loading.LoadingDto
import com.enderx.jobsearcher.core.component.loading.LoadingType
import com.enderx.jobsearcher.databinding.LayoutLoadingBinding

open class BaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    var isLoading = false
        private set

    open fun getLoadingBinding(): LayoutLoadingBinding? = null

    fun startLoading(loadingType: LoadingType) {
        val binding = getLoadingBinding() ?: return
        with(binding) {
            dto = LoadingDto(loadingType, true)
            executePendingBindings()
        }
        isLoading = true
    }

    fun stopLoading() {
        val binding = getLoadingBinding() ?: return
        with(binding) {
            dto = LoadingDto(LoadingType.TYPE_NO_LOADING, false)
            executePendingBindings()
        }
        isLoading = false
    }
}