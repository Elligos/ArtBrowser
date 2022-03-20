package com.example.deviantartviewer.data.authorization

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.deviantartviewer.data.remote.Endpoints
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.utils.log.Logger
import net.openid.appauth.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager constructor(private val context: Context){

    var authState : AuthState

    val clientId = "18500"
    val clientSecret = "20472a4501820a07035555938d28d607"

    companion object {
        private const val TAG = "AuthManager"
    }


    init {
        authState = AuthState()
    }

    fun updateCurrentAuthState( resp : AuthorizationResponse,  ex : AuthorizationException?){
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
        builder.setScope("basic")

        val authRequest = builder.build()
        val authService = AuthorizationService(context)
        return authService.getAuthorizationRequestIntent(authRequest)
    }

    fun handleAuthorizationResponse( intent : Intent){
        val resp = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        Logger.d(TAG, "Authorization response: $resp ")
        Logger.d(TAG, "Authorization code: ${resp?.authorizationCode} ")
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
            if (tokenResp != null) {
                // exchange succeeded
                authState.update(tokenResp, tokenEx)
                Logger.d(TAG, "Authorization succeeded ")
            } else {
                // authorization failed, check ex for more details
                Logger.d(TAG, "Authorization failed!")
            }
            Logger.d(TAG, "Token response: $tokenResp ")
            Logger.d(TAG, "Token exception: $tokenEx ")
            Logger.d(TAG, "Token: ${authState.accessToken} ")
        }
    }

    fun getCurrentToken() : String{
        return authState.accessToken!!
    }

}