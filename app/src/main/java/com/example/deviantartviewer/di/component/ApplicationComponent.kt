package com.example.deviantartviewer.di.component


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.local.db.DatabaseService
import com.example.deviantartviewer.data.local.db.dao.UserDao
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.di.ApplicationContext
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton
import  com.example.deviantartviewer.di.module.ApplicationModule
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import com.example.deviantartviewer.data.local.storage.AppStorageManager


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

    fun getAppStorageManager() : AppStorageManager

    fun getUserRepository(): UserRepository

    fun getImageRepository() : ImageRepository

    fun getDatabaseServer() : DatabaseService

    fun getUserDao() : UserDao
//
//
//
//    fun getViewPreloadSizeProvider() : ViewPreloadSizeProvider<String>


}