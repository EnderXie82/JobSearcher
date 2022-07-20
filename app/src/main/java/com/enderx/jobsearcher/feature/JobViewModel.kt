package com.enderx.jobsearcher.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enderx.jobsearcher.core.BaseViewModel
import com.enderx.jobsearcher.core.utils.Constant
import com.enderx.jobsearcher.feature.home.model.data.History
import com.enderx.jobsearcher.feature.home.model.response.GetJobsResponse
import com.enderx.jobsearcher.feature.home.model.response.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor() : BaseViewModel() {

    private var _historyList: MutableLiveData<ArrayDeque<History>> = MutableLiveData(ArrayDeque())
    val historyList: LiveData<ArrayDeque<History>> = _historyList

    private var _searchResult: MutableLiveData<List<Job>> = MutableLiveData()
    val searchResult: LiveData<List<Job>> = _searchResult

    private var _selectedJob: MutableLiveData<Job> = MutableLiveData()
    val selectedJob: LiveData<Job> = _selectedJob

    fun handleHistoryList(newType: String) {
        _historyList.value = _historyList.value?.apply {
            if (size == Constant.MAX_COUNT_OF_HISTORY_ITEM) {
                removeLast()
            }
            addFirst(History(newType))
        }
    }

    fun updateSearchResult(result: GetJobsResponse) {
        _searchResult.value = result.data.jobs
    }

    fun updateSelectedJob(job: Job) {
        _selectedJob.value = job
    }
}