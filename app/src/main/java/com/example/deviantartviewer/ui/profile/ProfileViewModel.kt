package com.example.deviantartviewer.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.converter.Converter
import com.example.deviantartviewer.data.model.User
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception


class ProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    authManager: AuthManager,
    private val userRepository: UserRepository
) : BaseViewModel(
    schedulerProvider, compositeDisposable, networkHelper, authManager
){

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    val imageUrl : MutableLiveData<String> = MutableLiveData()
    val imageUri : MutableLiveData<String> = MutableLiveData()

    val userInfo : MutableLiveData<User> = MutableLiveData()
    val launchLogin: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    var fetchInProcess : MutableLiveData<Boolean> = MutableLiveData()


    override fun onCreate() {
        Logger.d(TAG, "ProfileViewModel created")
        Logger.d(TAG, "Auth token: ${authManager.getCurrentToken()}")

        if(userRepository.getUserOutdated()) updateProfileDataFromNetwork()
        else loadProfileDataFromLocal()

        compositeDisposable.add(
                authManager.authCompleteSubject.subscribe {authorized ->
                    if (authorized == false) launchLogin.postValue(Event(emptyMap()))
                }
        )

    }

    private fun updateProfileDataFromNetwork(){
        fetchInProcess.postValue(true)
        compositeDisposable.add(
                userRepository.doUserProfileFetch()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Profile request result: $it")
                                    fetchInProcess.postValue(false)
                                    userRepository.setUserOutdated(false)
                                    updateProfileWithResponseData(it)
                                },
                                {
                                    fetchInProcess.postValue(false)
                                    Logger.d(TAG, "Profile request failed with exception: $it")
                                }
                        )
        )
    }

    private fun updateProfileWithResponseData(data : ProfileResponse){
        val user = Converter.convertToUser(data)
        userInfo.postValue(user)
        imageUrl.postValue(user.usericon_url)
    }

    fun saveImageToInternalStorage(bitmap : Bitmap){
        compositeDisposable.add(
                userRepository.saveImage(bitmap)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                val user = userInfo.value
                                if(user != null) {
                                    user.usericon_uri = it
                                    saveUserInfoToDb(user)
                                    Logger.d(TAG, "User icon successfully saved to the storage: $it")
                                }
                                else{
                                    Logger.d(TAG, "User is null !")
                                    throw Exception("user is null!")
                                }

                            },
                            {
                                Logger.d(TAG, "User icon save failed with exception: $it")
                            }
                        )
        )
    }


    private fun saveUserInfoToDb(user : User){
        val userEntity = Converter.convertToUserEntity(user)

        compositeDisposable.add(
                userRepository.saveUser(userEntity)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "User info successfully saved to the database!")
                                    userRepository.saveUserId(userEntity.userid)
                                },
                                {
                                    Logger.d(TAG, "User info save failed with exception: $it")
                                }
                        )
        )
    }

    private fun loadProfileDataFromLocal(){
        val userid = userRepository.getUserId()
        readUserInfoFromDb(userid)
    }

    private fun readUserInfoFromDb(userid : String){
        compositeDisposable.add(
                userRepository.getUser(userid)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "User info successfully read from database!")
                                    Logger.d(TAG, "User info: $it")
                                    val user = Converter.convertToUser(it)
                                    userInfo.postValue(user)
                                    imageUri.postValue(user.usericon_uri)
                                },
                                {
                                    Logger.d(TAG, "User info read failed with exception: $it")
                                }
                        )
        )
    }

    fun logout(){
        compositeDisposable.add(
                userRepository.logoutCall()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Logout request result: $it")
                                    authManager.authCompleteSubject.onNext(false)
                                },
                                {
                                    Logger.d(TAG, "Logout request failed with exception: $it")
                                }
                        )
        )

    }

}