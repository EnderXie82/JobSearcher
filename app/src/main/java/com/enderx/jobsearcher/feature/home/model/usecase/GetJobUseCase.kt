package com.enderx.jobsearcher.feature.home.model.usecase

import com.enderx.jobsearcher.core.BaseUseCase
import com.enderx.jobsearcher.feature.home.model.JobRepository
import com.enderx.jobsearcher.feature.home.model.response.GetJobsResponse
import org.json.JSONObject
import javax.inject.Inject

class GetJobUseCase @Inject constructor(
    private val jobRepository: JobRepository
) :
    BaseUseCase<GetJobsResponse, GetJobsResponse>() {

    private lateinit var type: String

    fun withType(type: String): GetJobUseCase {
        this.type = type
        return this
    }

    override fun requestData(): suspend () -> GetJobsResponse = {
        val jsonObject = JSONObject()
        jsonObject.put(
            "query", "{jobs(input:{type:\"$type\"}){title\ncompany{\n" +
                    "name\n" +
                    "logoUrl\n" +
                    "}\ndescription}}"
        )
        jobRepository.getJobs(jsonObject.toString())
    }

    override fun processData(response: GetJobsResponse): GetJobsResponse = response
}