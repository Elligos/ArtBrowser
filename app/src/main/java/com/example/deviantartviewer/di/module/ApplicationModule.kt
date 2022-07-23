package com.example.deviantartviewer.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.local.db.DatabaseService
import com.example.deviantartviewer.data.local.db.dao.UserDao
import com.example.deviantartviewer.data.remote.NetworkService
import com.example.deviantartviewer.data.remote.Networking
import com.example.deviantartviewer.di.ApplicationContext
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.RxSchedulerProvider
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import com.example.deviantartviewer.data.local.storage.AppStorageManager
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

    @Singleton
    @Provides
    fun provideAppStorageManager(): AppStorageManager = AppStorageManager(application)

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
            Networking.create(
                    //"20472a4501820a07035555938d28d607",
                    "https://www.deviantart.com/api/v1/oauth2/",
                    application.cacheDir,
                    10 * 1024 * 1024 // 10MB
            )

    @Provides
    @Singleton
    fun provideDatabaseService() : DatabaseService =
            Room.databaseBuilder(
                    application, DatabaseService::class.java,
                    "userinfo_database"
            ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: DatabaseService): UserDao = database.userDao()

}