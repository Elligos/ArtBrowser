package com.example.deviantartviewer.data.repository

import android.graphics.Bitmap
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.local.db.dao.UserDao
import com.example.deviantartviewer.data.local.db.entity.UserEntity
import com.example.deviantartviewer.data.local.prefs.UserPreferences
import com.example.deviantartviewer.data.local.storage.AppStorageManager
import com.example.deviantartviewer.data.remote.NetworkService
import com.example.deviantartviewer.data.remote.response.LogoutResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.remote.response.WhoamiResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val networkService: NetworkService,
                                         private val authManager : AuthManager,
                                         private val userDao: UserDao,
                                         private val storageManager: AppStorageManager,
                                         private val userPreferences: UserPreferences
){


    fun doUserProfileFetch() : Single<ProfileResponse> =
            networkService.doProfileGetCall("Bearer "+authManager.getCurrentToken(),
                                            ext_collections =false,
                                            ext_galleries = false,
                                            with_session = false,
                                            expand = "user.details,user.geo,user.stats")

    fun doWhoAmICall() : Single<WhoamiResponse> =
            networkService.doWhoamiCall("Bearer "+authManager.getCurrentToken(),
                                        with_session = false,
                                        mature_content = false)

    fun logoutCall() : Single<LogoutResponse> =
            networkService.doLogoutCall(authManager.getCurrentToken())

    fun saveUser(userEntity: UserEntity) : Completable = userDao.insertUserInfo(userEntity)

    fun getUser(userid : String) : Single<UserEntity> = userDao.getUser(userid)

    fun readUsers() : Single<List<UserEntity>> = userDao.getAll()

    fun saveImage(bitmap : Bitmap) : Single<String> =
                                        storageManager.saveBitmapToInternalStorage(bitmap)

    fun getUserOutdated() = userPreferences.getUserInfoOutdated()

    fun setUserOutdated(isOutdated : Boolean) = userPreferences.setUserInfoOutdated(isOutdated)

    fun saveUserId(id : String) = userPreferences.setUserId(id)

    fun getUserId() : String = userPreferences.getUserId()

}