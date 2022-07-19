package com.enderx.jobsearcher.feature.splash.presentation

import androidx.lifecycle.viewModelScope
import com.enderx.jobsearcher.core.BaseViewModel
import com.enderx.jobsearcher.core.datastore.DataStoreRepository
import com.enderx.jobsearcher.core.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) :
    BaseViewModel() {

    fun setFirstLaunched(value: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.putBoolean(Constant.PREFERENCE_KEY_IS_FIRST_LAUNCH, value)
        }
    }
}