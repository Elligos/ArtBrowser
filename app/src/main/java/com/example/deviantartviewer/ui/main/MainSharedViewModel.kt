package com.example.deviantartviewer.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager) {

    override fun onCreate() {}

    val homeRedirection = MutableLiveData<Event<Boolean>>()

    val profileRedirection = MutableLiveData<Event<Boolean>>()

    val detailedImage = MutableLiveData<Image>()
    val backToBrowse = MutableLiveData<Event<Boolean>>()

//    val newPost: MutableLiveData<Event<Post>> = MutableLiveData()



//    fun onHomeRedirect() {
//        homeRedirection.postValue(Event(true))
//    }
//
//    fun onProfileRedirect(){
//        profileRedirection.postValue(Event(true))
//    }


}