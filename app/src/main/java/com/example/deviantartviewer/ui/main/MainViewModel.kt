package com.example.deviantartviewer.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager,
        private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager) {

//    val profileNavigation = MutableLiveData<Event<Boolean>>()
//    val homeNavigation = MutableLiveData<Event<Boolean>>()
//
    override fun onCreate() {
//        if (!userRepository.isThemeChange()) {
//            homeNavigation.postValue(Event(true))
//
//        }else{
//
//            profileNavigation.postValue(Event(true))
//        }
//
//        userRepository.saveThemeChange(false)
//        //homeNavigation.postValue(Event(true))
    }


}