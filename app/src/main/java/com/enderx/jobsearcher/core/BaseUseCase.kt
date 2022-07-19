package com.enderx.jobsearcher.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseUseCase<T, M> {

    private val _successLiveData: MutableLiveData<T?> = MutableLiveData()
    val successLiveData: LiveData<T?> = _successLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    abstract fun requestData(): suspend () -> M

    abstract fun processData(response: M): T?

    fun onSuccess(data: T?) {
        _successLiveData.value = data
    }

    open fun onError() {}
}