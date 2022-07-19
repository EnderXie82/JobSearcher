package com.enderx.jobsearcher.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.enderx.jobsearcher.core.BaseViewModel
import com.enderx.jobsearcher.core.datastore.DataStoreRepository
import com.enderx.jobsearcher.core.utils.Constant.PREFERENCE_KEY_IS_FIRST_LAUNCH
import com.enderx.jobsearcher.core.utils.SingleLiveData
import com.enderx.jobsearcher.feature.home.model.usecase.GetJobUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val getJobUseCase: GetJobUseCase
) :
    BaseViewModel() {

    private var _isFirstLaunchLiveData: SingleLiveData<Boolean> = SingleLiveData()
    val isFirstLaunchLiveData: LiveData<Boolean> = _isFirstLaunchLiveData

    private var _isSearchBtnEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearchBtnEnabled: LiveData<Boolean> = _isSearchBtnEnabled

    private var _navigateToResult: SingleLiveData<Boolean> = SingleLiveData()
    val navigateToResult: LiveData<Boolean> = _navigateToResult

    val onSearchSuccess = Transformations.map(getJobUseCase.successLiveData) {
        _navigateToResult.setValue(true)
        return@map it
    }

    fun checkFirstLaunch() = runBlocking {
        val firstTime: Boolean? = dataStoreRepository.getBoolean(PREFERENCE_KEY_IS_FIRST_LAUNCH)
        if (firstTime == null || firstTime == false) {
            _isFirstLaunchLiveData.setValue(true)
        }
    }

    fun setSearchBtnEnable(value: Boolean) {
        _isSearchBtnEnabled.value = value
    }

    fun fetchJobs(type: String) {
        request(getJobUseCase.withType(type))
    }


}