package com.example.deviantartviewer.di.component


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.di.ApplicationContext
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton
import  com.example.deviantartviewer.di.module.ApplicationModule
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import dagger.BindsInstance





@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: DeviantArtApp)



    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context


    fun getSharedPreferences(): SharedPreferences

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable

    fun getNetworkHelper(): NetworkHelper

    fun getAuthManager(): AuthManager

    fun getUserRepository(): UserRepository

    fun getImageRepository() : ImageRepository
//
//
//
//    fun getViewPreloadSizeProvider() : ViewPreloadSizeProvider<String>


}