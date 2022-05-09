package com.example.deviantartviewer.ui.browse

import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class BrowseViewModel (
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager
) : BaseViewModel(
        schedulerProvider, compositeDisposable, networkHelper, authManager
){

    companion object {
        private const val TAG = "BrowseViewModel"
    }

    override fun onCreate() {
        Logger.d(TAG, "BrowseViewModel created!")
    }


}