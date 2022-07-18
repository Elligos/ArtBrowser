package com.example.deviantartviewer.data.remote

import com.example.deviantartviewer.data.remote.response.*
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
    ) : Single<CollectionsAllResponse>


    @POST(Endpoints.FAVE)
    @FormUrlEncoded
    fun doFaveCall(
            @Field("deviationid") deviationid : String,
            @Field("access_token") access_token : String
    ): Single<FaveResponse>

    @POST(Endpoints.UNFAVE)
    @FormUrlEncoded
    fun doUnfaveCall(
            @Field("deviationid") deviationid : String,
            @Field("access_token") access_token : String
    ): Single<FaveResponse>


    @POST(Endpoints.LOGOUT)
    @FormUrlEncoded
    fun doLogoutCall(
            @Field("token") token: String,
    ): Single<LogoutResponse>

}