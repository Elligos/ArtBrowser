package com.example.deviantartviewer


import android.app.Application

import android.content.SharedPreferences
import com.example.deviantartviewer.di.component.ApplicationComponent
import com.example.deviantartviewer.di.component.DaggerApplicationComponent
import com.example.deviantartviewer.di.module.ApplicationModule

import javax.inject.Inject


class DeviantArtApp : Application() {
    lateinit var applicationComponent: ApplicationComponent

//    @Inject
//    lateinit var preferences : SharedPreferences

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        //initTheme()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }



    // Needed to replace the component with a test specific one
    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }

//    private fun initTheme() {
//        ThemeManager.applyTheme(preferences.getString("PREF_THEME_MODE","")!!)
//    }


}