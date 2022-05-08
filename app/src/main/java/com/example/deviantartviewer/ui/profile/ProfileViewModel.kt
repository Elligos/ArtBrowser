package com.example.deviantartviewer.ui.profile

import android.content.Intent
import android.os.UserManager
import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
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
//        compositeDisposable.add(
//            userRepository.doWhoAmICall()
//                .subscribeOn(schedulerProvider.io())
//                .subscribe(
//                        {
//                            Logger.d(TAG, "WhoAmI request result: $it")
//                        },
//                        {
//                            Logger.d(TAG, "WhoAmI request failed with exception: $it")
//                        }
//                )
//        )

        compositeDisposable.add(
                userRepository.doUserProfileFetch()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Profile request result: $it")
                                    imageUrl.postValue(it.user?.usericon)
                                    username.postValue(it.user?.username)
                                    birthday.postValue("${it.user?.details?.age} years")
                                    country.postValue(it.user?.geo?.country)
                                    profileViews.postValue("${it.stats?.profilePageviews}")
                                    favorites.postValue("${it.stats?.userFavourites}")
                                    commentsMade.postValue("${it.stats?.userComments}")
                                    commentsReceived.postValue("${it.stats?.profileComments}")
                                    watchingYou.postValue("${it.user?.userStats?.watchers}")
                                    youWatching.postValue("${it.user?.userStats?.friends}")
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

    fun getLogoutIntent() : Intent = authManager.getLogoutRequestIntent()

    fun logout(){

        compositeDisposable.add(
                userRepository.logoutCall()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(                                {
                            Logger.d(TAG, "Logout call result: $it")
                            authManager.authCompleteSubject.onNext(false)
                        },
                        {
                            Logger.d(TAG, "Logout call failed with exception: $it")
                        })
        )
        //authManager.authCompleteSubject.onNext(false)
    }



}