package com.example.deviantartviewer.ui.profile

import android.content.Intent
import android.os.UserManager
import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable



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
    val username : MutableLiveData<String> = MutableLiveData()
    val birthday : MutableLiveData<String> = MutableLiveData()
    val country : MutableLiveData<String> = MutableLiveData()
    val profileViews : MutableLiveData<String> = MutableLiveData()
    val watchingYou : MutableLiveData<String> = MutableLiveData()
    val youWatching : MutableLiveData<String> = MutableLiveData()
    val favorites : MutableLiveData<String> = MutableLiveData()
    val commentsMade : MutableLiveData<String> = MutableLiveData()
    val commentsReceived : MutableLiveData<String> = MutableLiveData()

    val launchLogin: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()


    override fun onCreate() {
        Logger.d(TAG, "ProfileViewModel created")
        Logger.d(TAG, "Auth token: ${authManager.getCurrentToken()}")

        compositeDisposable.add(
                userRepository.doUserProfileFetch()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Profile request result: $it")
                                    updateProfileWithResponseData(it)
                                },
                                {
                                    Logger.d(TAG, "Profile request failed with exception: $it")
                                }
                        )
        )

        compositeDisposable.add(
                authManager.authCompleteSubject.subscribe {authorized ->
                    if (authorized == false) launchLogin.postValue(Event(emptyMap()))
                }
        )

    }

    fun updateProfileWithResponseData(data : ProfileResponse){

        val bigAvatarUrl = convertToBigAvatarUrl(data.user?.usericon?:"")

        imageUrl.postValue(bigAvatarUrl)
        username.postValue(data.user?.username)
        birthday.postValue("${data.user?.details?.age} years")
        country.postValue(data.user?.geo?.country)
        profileViews.postValue("${data.stats?.profilePageviews}")
        favorites.postValue("${data.stats?.userFavourites}")
        commentsMade.postValue("${data.stats?.userComments}")
        commentsReceived.postValue("${data.stats?.profileComments}")
        watchingYou.postValue("${data.user?.userStats?.watchers}")
        youWatching.postValue("${data.user?.userStats?.friends}")
    }

    fun convertToBigAvatarUrl(avatarUrl : String) : String{
        return avatarUrl.replace("https://a.deviantart.net/avatars",
                                "https://a.deviantart.net/avatars-big")
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