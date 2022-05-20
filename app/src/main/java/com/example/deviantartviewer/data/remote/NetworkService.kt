package com.example.deviantartviewer.data.remote

import com.example.deviantartviewer.data.remote.request.LogoutRequest
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.remote.response.WhoamiResponse
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.WHOAMI)
    fun doWhoamiCall(
            @Header("Authorization") token: String,
            @Query("with_session") with_session: Boolean?,
            @Query("mature_content") mature_content: Boolean?
    ): Single<WhoamiResponse>

    @GET(Endpoints.PROFILE)
    fun doProfileGetCall(
            @Header("Authorization") token: String,
            @Query("ext_collections") ext_collections: Boolean,
            @Query("ext_galleries") ext_galleries: Boolean,
            @Query("with_session") with_session: Boolean?,
            @Query("expand") expand: String
    ): Single<ProfileResponse>

    @GET(Endpoints.NEWEST)
    fun doNewestFetchCall(
            @Header("Authorization") token: String,
            @Query("q") query: String?,
            @Query("offset") offset: Int?,
            @Query("limit") limit: Int?,
            @Query("mature_content") mature_content: Boolean?
    ): Single<ImageResponse>

    @GET(Endpoints.COLLECTIONS_ALL)
    fun doCollectionsAllFetchCall(
            @Header("Authorization") token: String,
            @Query("offset") offset: Int?,
            @Query("limit") limit: Int?,
    ) : Single<ImageResponse>

    @POST(Endpoints.LOGOUT)
    fun doLogoutCall(
//            @Header("Authorization") authToken: String,
//            @Query("token") token: String,
//            @Query("revoke_refresh_only") revoke_refresh_only: Boolean
            @Body logoutRequest: LogoutRequest
    ): Single<String>

}