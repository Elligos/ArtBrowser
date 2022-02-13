package com.example.deviantartviewer.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.login.LoginViewModel
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.ui.signup.SignupViewModel
import com.example.deviantartviewer.utils.ViewModelProviderFactory
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {


    @Provides
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper//,
        //userRepository: UserRepository
    ): LoginViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(schedulerProvider, compositeDisposable, networkHelper/*, userRepository*/)
        }).get(LoginViewModel::class.java)


    @Provides
    fun provideSignupViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper//,
        //userRepository: UserRepository
    ): SignupViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(SignupViewModel::class) {
            SignupViewModel(schedulerProvider, compositeDisposable, networkHelper/*, userRepository*/)
        }).get(SignupViewModel::class.java)

    @Provides
    fun provideProfileViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper//,
            //userRepository: UserRepository
    ): ProfileViewModel = ViewModelProvider(
            fragment, ViewModelProviderFactory(ProfileViewModel::class) {
        ProfileViewModel(schedulerProvider, compositeDisposable, networkHelper/*, userRepository*/)
    }).get(ProfileViewModel::class.java)

}