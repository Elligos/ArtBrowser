package com.example.deviantartviewer.di.component

import com.example.deviantartviewer.di.FragmentScope
import com.example.deviantartviewer.di.module.FragmentModule
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.ui.profile.ProfileFragment
import com.example.deviantartviewer.ui.signup.SignupFragment

import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {



    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignupFragment)
    fun inject(fragment: ProfileFragment)


}