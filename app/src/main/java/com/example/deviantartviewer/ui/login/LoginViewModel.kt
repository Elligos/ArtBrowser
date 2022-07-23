package com.example.deviantartviewer.ui.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.common.Resource
import com.example.deviantartviewer.utils.common.Validator
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    authManager: AuthManager,
    private val userRepository: UserRepository
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper, authManager
){

    override fun onCreate() {}

    val emailField : MutableLiveData<String> = MutableLiveData()
    val passwordField : MutableLiveData<String> = MutableLiveData()
    val launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()


    fun onEmailChange(email : String) = emailField.postValue(email)

    fun onPasswordChange(password : String) = passwordField.postValue(password)

    fun getAuthIntent() : Intent{
        return authManager.getAuthorizationRequestIntent()
    }

    fun requestTokenWithData(data : Intent){
        authManager.handleAuthorizationResponse(data)
        authManager.requestToken()
        compositeDisposable.add(
            authManager.authCompleteSubject.subscribe {authorized ->
                if (authorized == true) launchMain.postValue(Event(emptyMap()))
            }
        )
    }

    fun setProfileNeedUpdate(){
        userRepository.setUserOutdated(true)
    }


}