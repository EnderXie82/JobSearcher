package com.enderx.jobsearcher.feature.home.model

import com.enderx.jobsearcher.core.network.NetworkConstant
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class JobRepository @Inject constructor(
    @Named(NetworkConstant.RETROFIT_REST) retrofit: Retrofit
) {
    private val apiJob = retrofit.create(JobApi::class.java)

    suspend fun getJobs(query: String) = apiJob.queryJobs(query)
}