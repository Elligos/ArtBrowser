package com.example.deviantartviewer.di.component


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.di.ApplicationContext
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton
import  com.example.deviantartviewer.di.module.ApplicationModule
import dagger.BindsInstance

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: DeviantArtApp)



    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context


//    fun getSharedPreferences(): SharedPreferences
//
//
//
//    fun getCompositeDisposable(): CompositeDisposable
//
//
//
//    fun getViewPreloadSizeProvider() : ViewPreloadSizeProvider<String>


}