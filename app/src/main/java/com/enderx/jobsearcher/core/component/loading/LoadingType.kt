package com.enderx.jobsearcher.core.component.loading

import com.enderx.jobsearcher.R

enum class LoadingType {

    TYPE_NO_LOADING {
        override fun getLoadingFile(): String = ""
        override fun getLoadingRes(): Int = 0
    },
    TYPE_SPINNER {
        override fun getLoadingFile(): String = ""
        override fun getLoadingRes(): Int = R.anim.rotate_loading
    },
    TYPE_CUSTOM {
        override fun getLoadingFile(): String = ""
        override fun getLoadingRes(): Int = 0
    };

    abstract fun getLoadingFile(): String
    abstract fun getLoadingRes(): Int
}