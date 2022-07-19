package com.enderx.jobsearcher.core.component.toolbar

import android.view.View
import androidx.annotation.DrawableRes

data class ToolbarData(
    val title: String = "",
    val showNavigationIcon: Boolean? = null,
    @DrawableRes val navigationIcon: Int? = null,
    val onNavigationClickListener: View.OnClickListener? = null
) {
    companion object {
        val TOOLBAR_NO_DATA = ToolbarData()
    }
}
