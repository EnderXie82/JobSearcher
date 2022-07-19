package com.enderx.jobsearcher.feature.home.model.response

import androidx.annotation.Keep

@Keep
data class Job(
    val company: Company,
    val description: String,
    val title: String
)