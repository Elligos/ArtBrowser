package com.example.deviantartviewer.di.component

import com.example.deviantartviewer.di.FragmentScope
import com.example.deviantartviewer.di.module.FragmentModule
import com.example.deviantartviewer.ui.browse.BrowseFragment
import com.example.deviantartviewer.ui.fullimage.ImageFragment
import com.example.deviantartviewer.ui.favorites.FavoritesFragment
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.ui.profile.ProfileFragment


import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: LoginFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: BrowseFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: ImageFragment)

}