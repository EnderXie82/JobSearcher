package com.enderx.jobsearcher.core

import android.view.View
import androidx.lifecycle.*
import com.enderx.jobsearcher.core.component.loading.LoadingType
import com.enderx.jobsearcher.core.component.toolbar.ToolbarData
import com.enderx.jobsearcher.core.component.toolbar.ToolbarDto
import com.enderx.jobsearcher.core.network.response.BaseResponse
import com.enderx.jobsearcher.core.utils.SingleLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    private val loadingLiveData: SingleLiveData<Pair<LoadingType, Boolean>> = SingleLiveData()
    private val _toolbarDtoLiveData: MutableLiveData<ToolbarDto> = MutableLiveData()
    val toolbarDtoLiveData: LiveData<ToolbarDto> = _toolbarDtoLiveData

    fun observeLoading(
        lifecycleOwner: LifecycleOwner,
        handleLoading: (pair: Pair<LoadingType, Boolean>) -> Unit
    ) {
        loadingLiveData.observe(lifecycleOwner, Observer {
            if (it.first == LoadingType.TYPE_CUSTOM) {
                if (it.second) {
                    startLoading()
                } else {
                    stopLoading()
                }
            } else if (it.first != LoadingType.TYPE_NO_LOADING) {
                handleLoading.invoke(it)
            }
        })
    }

    fun <T, M> request(
        useCase: BaseUseCase<T, M>,
        loadingType: LoadingType = LoadingType.TYPE_SPINNER
    ) {
        loadingLiveData.setValue(loadingType to true)
        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            useCase.onError()
            loadingLiveData.setValue(loadingType to false)
        }) {
            val result = withContext(Dispatchers.IO) {
                useCase.requestData().invoke()
            }

            if (when (result) {
                    is BaseResponse -> true
                    else -> true
                }
            ) {
                val finalResult = withContext(Dispatchers.Default) {
                    useCase.processData(result)
                }

                withContext(Dispatchers.Main) {
                    useCase.onSuccess(finalResult)
                    loadingLiveData.setValue(loadingType to false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    useCase.onError()
                    loadingLiveData.setValue(loadingType to false)
                }
            }
        }
    }

    fun updateToolbar(toolbarData: ToolbarData, listener: View.OnClickListener) {
        _toolbarDtoLiveData.value = if (toolbarData != ToolbarData.TOOLBAR_NO_DATA) {
            ToolbarDto(toolbarData, listener, true)
        } else {
            ToolbarDto()
        }
    }

    protected open fun startLoading() {

    }

    protected open fun stopLoading() {

    }
}