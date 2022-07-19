package com.enderx.jobsearcher.core.component.toolbar

import android.view.View
import androidx.annotation.DrawableRes
import com.enderx.jobsearcher.R

data class ToolbarDto(
    private val data: ToolbarData? = null,
    private val listener: View.OnClickListener? = null,
    val visible: Boolean = false
) {
    val title: String? = data?.title
    val showNavigationIcon: Boolean = data?.showNavigationIcon ?: true

    @DrawableRes
    val navigationIcon: Int = data?.navigationIcon ?: R.drawable.ic_baseline_arrow_back_24
    val onNavigationClickListener = data?.onNavigationClickListener ?: listener
}