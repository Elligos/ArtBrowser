package com.example.deviantartviewer.data.repository

import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.remote.NetworkService
import com.example.deviantartviewer.data.remote.request.LogoutRequest
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.remote.response.WhoamiResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val networkService: NetworkService,
                                         private val authManager : AuthManager
){

    fun doNewestImagesFetch(query : String) : Single<ImageResponse> =
            networkService.doNewestFetchCall("Bearer "+authManager.getCurrentToken(),
                                              query,
                                            0,
                                            30,
                                            true)

    fun doCollectionsAllFetch() : Single<ImageResponse> =
            networkService.doCollectionsAllFetchCall("Bearer "+authManager.getCurrentToken(),
                                                    0,
                                                    14)

}