package com.example.deviantartviewer.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.di.ApplicationContext
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

//    @Provides
//    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
//
//
//    @Provides
//    @Singleton
//    fun provideSharedPreferences(): SharedPreferences =
//        application.getSharedPreferences("bootcamp-instagram-project-prefs", Context.MODE_PRIVATE)
//
//    @Provides
//    @Singleton
//    fun provideViewPreloadSizeProvider(): ViewPreloadSizeProvider<String> = ViewPreloadSizeProvider<String>()




}