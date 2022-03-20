package com.example.deviantartviewer.ui.profile

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable



class ProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    authManager: AuthManager
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper, authManager
){

    override fun onCreate() {}



}