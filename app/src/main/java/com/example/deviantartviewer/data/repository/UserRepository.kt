package com.example.deviantartviewer.data.repository

import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.remote.NetworkService
import com.example.deviantartviewer.data.remote.response.LogoutResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.remote.response.WhoamiResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val networkService: NetworkService,
                                         private val authManager : AuthManager
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

}