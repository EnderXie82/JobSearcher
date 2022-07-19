package com.enderx.jobsearcher.feature.home.model

import com.enderx.jobsearcher.core.converter.ConverterFormat
import com.enderx.jobsearcher.core.converter.RequestFormat
import com.enderx.jobsearcher.core.converter.ResponseFormat
import com.enderx.jobsearcher.core.network.NetworkConstant
import com.enderx.jobsearcher.feature.home.model.response.GetJobsResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface JobApi {

    @Headers(
        NetworkConstant.HEADER_CONTENT_TYPE_KEY_VALUE_JASON,
        NetworkConstant.HEADER_CONTENT_ACCEPT_TYPE_KEY_VALUE_JASON
    )
    @POST("/")
    @RequestFormat(ConverterFormat.SCALAR)
    @ResponseFormat(ConverterFormat.JSON)
    suspend fun queryJobs(@Body body: String): GetJobsResponse
}