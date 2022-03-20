package com.example.deviantartviewer.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.di.ApplicationContext
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.RxSchedulerProvider
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: DeviantArtApp) {


    @Singleton
    @Provides
    fun provideApplication(): Application = application


    @Singleton
    @ApplicationContext
    @Provides
    fun provideContext(): Context = application


    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
            application.getSharedPreferences("deviantArtPreferences", Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Singleton
    @Provides
    fun provideAuthManager(): AuthManager = AuthManager(application)

}