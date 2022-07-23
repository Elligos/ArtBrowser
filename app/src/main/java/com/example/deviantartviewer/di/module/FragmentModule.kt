package com.example.deviantartviewer.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.BrowseViewModel
import com.example.deviantartviewer.ui.fullimage.ImageViewModel
import com.example.deviantartviewer.ui.favorites.FavoritesViewModel
import com.example.deviantartviewer.ui.login.LoginViewModel
import com.example.deviantartviewer.ui.main.MainSharedViewModel
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.ViewModelProviderFactory
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {


    @Provides
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager,
        userRepository: UserRepository
    ): LoginViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager, userRepository)
        }).get(LoginViewModel::class.java)



    @Provides
    fun provideProfileViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager,
            userRepository: UserRepository
    ): ProfileViewModel = ViewModelProvider(
            fragment, ViewModelProviderFactory(ProfileViewModel::class) {
        ProfileViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager, userRepository)
    }).get(ProfileViewModel::class.java)

    @Provides
    fun provideFavoritesViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager,
            imageRepository : ImageRepository
    ): FavoritesViewModel = ViewModelProvider(
            fragment, ViewModelProviderFactory(FavoritesViewModel::class) {
        FavoritesViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager, imageRepository)
    }).get(FavoritesViewModel::class.java)

    @Provides
    fun provideBrowseViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager,
            imageRepository : ImageRepository
    ): BrowseViewModel = ViewModelProvider(
            fragment, ViewModelProviderFactory(BrowseViewModel::class) {
        BrowseViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager, imageRepository)
    }).get(BrowseViewModel::class.java)

    @Provides
    fun provideImageViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager,
            imageRepository : ImageRepository,
            userRepository: UserRepository
    ): ImageViewModel = ViewModelProvider(
            fragment, ViewModelProviderFactory(ImageViewModel::class) {
        ImageViewModel(schedulerProvider, compositeDisposable, networkHelper, authManager, imageRepository, userRepository)
    }).get(ImageViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            authManager: AuthManager
    ): MainSharedViewModel = ViewModelProvider(
            fragment.activity!!, ViewModelProviderFactory(MainSharedViewModel::class) {
        MainSharedViewModel(schedulerProvider, compositeDisposable, networkHelper,authManager)
    }).get(MainSharedViewModel::class.java)


}