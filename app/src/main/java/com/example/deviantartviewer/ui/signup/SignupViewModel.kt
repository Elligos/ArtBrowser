package com.example.deviantartviewer.ui.signup

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SignupViewModel (
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper

) : BaseViewModel(
        schedulerProvider, compositeDisposable, networkHelper
) {

    val usernameField : MutableLiveData<String> = MutableLiveData()
    val emailField : MutableLiveData<String> = MutableLiveData()
    val passwordField : MutableLiveData<String> = MutableLiveData()



    fun onUsernameChange(username : String) = usernameField.postValue(username)

    fun onEmailChange(email : String) = emailField.postValue(email)

    fun onPasswordChange(password : String) = passwordField.postValue(password)

    override fun onCreate() {
//        TODO("Not yet implemented")
    }

    fun doSignup(){
//        TODO("Not yet implemented")
    }


}