package com.enderx.jobsearcher.feature.home.model.response

import androidx.annotation.Keep

@Keep
data class Company(
    val logoUrl: String,
    val name: String
)