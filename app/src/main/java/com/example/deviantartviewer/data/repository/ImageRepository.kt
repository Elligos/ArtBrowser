package com.example.deviantartviewer.data.repository

import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.remote.NetworkService
import com.example.deviantartviewer.data.remote.request.FaveRequest
import com.example.deviantartviewer.data.remote.request.LogoutRequest
import com.example.deviantartviewer.data.remote.response.*
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val networkService: NetworkService,
                                         private val authManager : AuthManager
){

    fun doNewestImagesFetch(query : String, offset : Int, limit : Int) : Single<ImageResponse> =
            networkService.doNewestFetchCall(   token = "Bearer " + authManager.getCurrentToken(),
                                                query,
                                                offset,
                                                limit,
                                                mature_content = true   )

    fun doCollectionsAllFetch(offset : Int, limit : Int) : Single<CollectionsAllResponse> =
            networkService.doCollectionsAllFetchCall( token = "Bearer " + authManager.getCurrentToken(),
                                                      offset,
                                                      limit)


    fun faveCall(deviationid : String) : Single<FaveResponse> =
            networkService.doFaveCall(  deviationid,
                                        authManager.getCurrentToken() )

    fun unfaveCall(deviationid : String) : Single<FaveResponse> =
            networkService.doUnfaveCall(  deviationid,
                                          authManager.getCurrentToken() )



}

