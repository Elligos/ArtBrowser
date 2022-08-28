package com.example.deviantartviewer.ui.base

import androidx.lifecycle.ViewModel
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.utils.network.ConnectionLiveData
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper,
    protected val authManager: AuthManager
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun isNetworkConnected(): Boolean = networkHelper.isNetworkConnected()

    fun getNetworkConnected() : ConnectionLiveData{
        return networkHelper.connectionLiveData
    }

    abstract fun onCreate()
}