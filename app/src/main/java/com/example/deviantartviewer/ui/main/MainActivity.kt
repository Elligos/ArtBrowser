package com.example.deviantartviewer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.deviantartviewer.R
import com.example.deviantartviewer.utils.log.Logger
import com.jakewharton.retrofit2.adapter.rxjava2.Result.response
import net.openid.appauth.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

//        val data = getIntent().data
//        if(data != null){
//            val code = data.getQueryParameter("code")
//            Toast.makeText(this, "$code", Toast.LENGTH_LONG).show()
//        }

    }

//    var authState : AuthState? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
//        if(data == null) {
//            Logger.d(TAG, "onActivityResult data is null!")
//            return
//        }
//
//
//        if (requestCode == 19777) {
//            val resp = AuthorizationResponse.fromIntent(data)
//            val ex = AuthorizationException.fromIntent(data)
//
//            Logger.d(TAG, "Response: $resp ")
//            Logger.d(TAG, "Exception: $ex ")
//        } else {
//            val resp = AuthorizationResponse.fromIntent(data)
//            val ex = AuthorizationException.fromIntent(data)
//            Logger.d(TAG, "Request code : $requestCode  invalid!")
//            Logger.d(TAG, "Response: $resp ")
//            Logger.d(TAG, "Authorization code: ${resp?.authorizationCode} ")
//            Logger.d(TAG, "Exception: $ex ")
//            authState = AuthState(resp, ex)
//
//        }
    }

//    private fun retrieveTokens(authResponse: AuthorizationResponse) {
//        val tokenRequest: TokenRequest = authResponse.createTokenExchangeRequest()
//        val service = AuthorizationService(this)
//        service.performTokenRequest(tokenRequest, mClientAuthentication
//        ) { tokenResponse, tokenException ->
//            mAuthState.update(tokenResponse, tokenException)
//
//            // Handle token response error here
//            persistAuthState(mAuthState)
//        }
//    }
}