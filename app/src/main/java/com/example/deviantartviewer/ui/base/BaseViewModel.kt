package com.example.deviantartviewer.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.utils.common.Resource
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.ConnectionLiveData
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection

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

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()

    protected fun isNetworkConnected(): Boolean = networkHelper.isNetworkConnected()

    fun getNetworkConnected() : ConnectionLiveData{
        return networkHelper.connectionLiveData
    }

    abstract fun onCreate()
}