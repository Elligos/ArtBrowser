package com.example.deviantartviewer.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseActivity
import com.example.deviantartviewer.ui.main.MainViewModel
import com.example.deviantartviewer.utils.ViewModelProviderFactory
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>){

    @Provides
    fun provideMainViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager,
            userRepository: UserRepository
    ): MainViewModel = ViewModelProvider(
            activity, ViewModelProviderFactory(MainViewModel::class) {
        MainViewModel(schedulerProvider, compositeDisposable, networkHelper,authManager, userRepository)
    }).get(MainViewModel::class.java)

}