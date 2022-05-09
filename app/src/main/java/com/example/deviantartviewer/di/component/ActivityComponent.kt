package com.example.deviantartviewer.di.component

import com.example.deviantartviewer.di.ActivityScope
import com.example.deviantartviewer.di.module.ActivityModule
import com.example.deviantartviewer.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [ActivityModule::class]
)
interface ActivityComponent {


    fun inject(activity: MainActivity)
}