package com.example.deviantartviewer.data.authorization

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.deviantartviewer.BuildConfig
import com.example.deviantartviewer.data.remote.Endpoints
import com.example.deviantartviewer.utils.log.Logger
import io.reactivex.subjects.BehaviorSubject
import net.openid.appauth.*
import javax.inject.Singleton


@Singleton
class AuthManager constructor(private val context: Context){

    var authState : AuthState

    val authCompleteSubject = BehaviorSubject.createDefault(false)

    private val clientId = BuildConfig.API_KEY
    private val clientSecret = BuildConfig.API_SECRET

    companion object {
        private const val TAG = "AuthManager"
    }


    init {
        authState = AuthState()
    }

    fun updateCurrentAuthState(resp: AuthorizationResponse, ex: AuthorizationException?){
        authState.update(resp, ex)
    }

    fun getCurrentAuthState() : AuthState =  authState

    fun getAuthorizationRequestIntent() : Intent {
        val serviceConfig = AuthorizationServiceConfiguration(
                Uri.parse(Endpoints.AUTHORIZE),
                Uri.parse(Endpoints.TOKEN)
        )

        val redirectUri = Uri.parse("com.example.deviantartviewer://oauth2")
        val builder = AuthorizationRequest.Builder(
                serviceConfig,
                clientId,
                ResponseTypeValues.CODE,
                redirectUri
        )
        builder.setScopes("browse", "collection")

        val authRequest = builder.build()
        val authService = AuthorizationService(context)
        return authService.getAuthorizationRequestIntent(authRequest)
    }

    fun handleAuthorizationResponse(intent: Intent){
        val resp = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        Logger.d(TAG, "Authorization response: $resp ")
        Logger.d(TAG, "Authorization code: ${resp?.authorizationCode} ")
        Logger.d(TAG, "Authorization idToken: ${resp?.idToken} ")
        Logger.d(TAG, "Authorization exception: $ex ")



        if(resp == null){
            Logger.d(TAG, "Authorization response: $resp  is null")
            return
        }
        authState.update(resp, ex)
    }

    fun requestToken(){

        val authResponse = authState.lastAuthorizationResponse
        if(authResponse == null){
            Logger.d(TAG, "Authorization response is empty, cannot request token! ")
            return
        }

        val clientAuth: ClientAuthentication = ClientSecretBasic(clientSecret)

        AuthorizationService(context).performTokenRequest(
                authResponse.createTokenExchangeRequest(), clientAuth) { tokenResp, tokenEx ->
            var authComplete = false
            if (tokenResp != null) {
                // exchange succeeded
                authState.update(tokenResp, tokenEx)
                Logger.d(TAG, "Authorization succeeded ")
                authComplete = true
            } else {
                // authorization failed, check ex for more details
                Logger.d(TAG, "Authorization failed!")
            }
            Logger.d(TAG, "Token response: $tokenResp ")
            Logger.d(TAG, "Token exception: $tokenEx ")
            Logger.d(TAG, "Token: ${authState.accessToken} ")
            Logger.d(TAG, "Token expiration time: ${authState.accessTokenExpirationTime} ")
            Logger.d(TAG, "idToken from response: ${tokenResp?.idToken} ")
//            authCompleteSubject.onNext(authComplete)
            refreshToken()
        }
    }

    fun refreshToken(){

        val clientAuth: ClientAuthentication = ClientSecretBasic(clientSecret)

        AuthorizationService(context).performTokenRequest(
                authState.createTokenRefreshRequest(), clientAuth) { tokenResp, tokenEx ->
            var authComplete = false
            if (tokenResp != null) {
                // exchange succeeded
                authState.update(tokenResp, tokenEx)
                Logger.d(TAG, "refreshToken() succeeded ")
                authComplete = true
            } else {
                // authorization failed, check ex for more details
                Logger.d(TAG, "refreshToken() failed!")
            }
            Logger.d(TAG, "Refresh token response: $tokenResp ")
            Logger.d(TAG, "Refresh token exception: $tokenEx ")
            Logger.d(TAG, "Refresh token: ${authState.accessToken} ")
            Logger.d(TAG, "Refresh token expiration time: ${authState.accessTokenExpirationTime} ")
            Logger.d(TAG, "idToken from refresh token response: ${tokenResp?.idToken} ")
            authCompleteSubject.onNext(authComplete)
        }

    }

    fun getCurrentToken() : String{
        return authState.accessToken!!
    }

    fun getLogoutRequestIntent() : Intent{

        val serviceConfig = AuthorizationServiceConfiguration(
                Uri.parse(Endpoints.AUTHORIZE),
                Uri.parse(Endpoints.TOKEN),
                null,
                Uri.parse(Endpoints.LOGOUT)
        )
        val redirectUri = Uri.parse("com.example.deviantartviewer://oauth2")

        Logger.d(TAG, "idToken: ${authState.idToken} ")
        Logger.d(TAG, "parsedIdToken: ${authState.parsedIdToken} ")
        Logger.d(TAG, "idToken from response: ${authState.lastTokenResponse?.idToken} ")



        val authService = AuthorizationService(context)
        var idTokenHint : String? = ""
        authState.performActionWithFreshTokens(authService){accessToken, idToken, ex ->
            Logger.d(TAG, "Fresh access token: $accessToken ")
            Logger.d(TAG, "Fresh idToken: $idToken ")
            Logger.d(TAG, "Fresh access token ex: $ex ")
            idTokenHint = idToken
        }

        val endSessionRequest = EndSessionRequest.Builder(serviceConfig)
                .setIdTokenHint(idTokenHint)
                .setPostLogoutRedirectUri(redirectUri)
                .build()


        return authService.getEndSessionRequestIntent(endSessionRequest)
    }

}