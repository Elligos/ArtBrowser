package com.example.deviantartviewer.ui.login

import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.utils.log.Logger
import net.openid.appauth.*


class LoginFragment : BaseFragment<LoginViewModel>() {

    //View Binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    var authService : AuthorizationService? = null// TODO: delete later!
    var authRequestBuilder: AuthorizationRequest.Builder? = null// TODO: delete later!

    override fun provideLayoutId(): Int   = R.layout.fragment_login

    override fun setupView(view: View) {
        _binding = FragmentLoginBinding.bind(view)

        binding.etEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

        })


        binding.etPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }
        })


        binding.buttonLogin.setOnClickListener{
            viewModel.doLogin()
//            showMessage("Login with email: ${viewModel.emailField.value} " +
//                                "and pswd: ${viewModel.passwordField.value} !")
            //findNavController().navigate(R.id.action_LoginFragment_to_profileFragment)

//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://www.deviantart.com/oauth2/authorize?response_type=code&client_id=18500&redirect_uri=deviantartviewer://oauth2&scope=basic&state=mysessionid")
//
//
//            //if (intent.resolveActivity(context!!.packageManager) != null) {
//                startActivity(intent)
//            //}


            val serviceConfig = AuthorizationServiceConfiguration(
                    Uri.parse("https://www.deviantart.com/oauth2/authorize"), // authorization endpoint
                    Uri.parse("https://www.deviantart.com/oauth2/token") // token endpoint

            )
            val clientId = "18500"
            val redirectUri = Uri.parse("com.example.deviantartviewer://oauth2")
            val builder = AuthorizationRequest.Builder(
                    serviceConfig,
                    clientId,
                    ResponseTypeValues.CODE,
                    redirectUri
            )
            builder.setScope("basic")

            val authRequest = builder.build()
            authRequestBuilder = builder
//            val authService = AuthorizationService(context!!)
            authService = AuthorizationService(context!!)
            val authIntent = authService?.getAuthorizationRequestIntent(authRequest)
            startActivityForResult(authIntent, 19777)

//            val serviceConfig = AuthorizationServiceConfiguration(
//                    Uri.parse("https://unsplash.com/oauth/authorize"), // authorization endpoint
//                    Uri.parse("https://unsplash.com/oauth/token") // token endpoint
//            )
//            val clientId = "JIk0bM-AwOH8mSRSqzrEi4aLtleSNyWXOwXZRla5OO8"
//            val redirectUri = Uri.parse("com.example.deviantartviewer://oauth2") //"urn:ietf:wg:oauth:2.0:oob"
//            val builder = AuthorizationRequest.Builder(
//                    serviceConfig,
//                    clientId,
//                    ResponseTypeValues.CODE,
//                    redirectUri
//            )
//            builder.setScopes("public")
//
//            val authRequest = builder.build()
////            val authService = AuthorizationService(context!!)
//            authService = AuthorizationService(context!!)
//            val authIntent = authService?.getAuthorizationRequestIntent(authRequest)
//            startActivityForResult(authIntent, 19777)

        }


        binding.textviewSignupMessage.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    companion object {
        private const val TAG = "LoginFragment"
    }
    var authState : AuthState? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if(data == null) {
            Logger.d(TAG, "onActivityResult data is null!")
            return
        }

        if (requestCode == 19777) Logger.d(TAG, "Request code : $requestCode valid")
        else Logger.d(TAG, "Request code : $requestCode  invalid!")

        val resp = AuthorizationResponse.fromIntent(data)
        val ex = AuthorizationException.fromIntent(data)
        Logger.d(TAG, "Response: $resp ")
        Logger.d(TAG, "Authorization code: ${resp?.authorizationCode} ")
        Logger.d(TAG, "Exception: $ex ")


        if(resp == null){
            Logger.d(TAG, "Response: $resp  is null")
            return
        }
        authState = AuthState(resp, ex)


//        val responseBuilder =
//                AuthorizationResponse.Builder(authRequestBuilder!!.build())
//        responseBuilder.setState(resp.state)
//        responseBuilder.setTokenType(resp.tokenType)
//        responseBuilder.setAdditionalParameters(resp.additionalParameters)
//        responseBuilder.setAuthorizationCode(resp.authorizationCode)
//        responseBuilder.setScope(null)
//        val authResponse : AuthorizationResponse = responseBuilder.build()

        val clientAuth: ClientAuthentication = ClientSecretBasic("20472a4501820a07035555938d28d607")

        authService?.performTokenRequest(
                resp.createTokenExchangeRequest(), clientAuth) { tokenResp, tokenEx ->
            if (tokenResp != null) {
                // exchange succeeded
                authState?.update(tokenResp, tokenEx)
                Logger.d(TAG, "Authorization succeeded ")
                Logger.d(TAG, "Token response: $tokenResp ")
                Logger.d(TAG, "Token exception: $tokenEx ")
                Logger.d(TAG, "Token: ${authState?.accessToken} ")
            } else {
                // authorization failed, check ex for more details
                Logger.d(TAG, "Authorization failed!")
                Logger.d(TAG, "Token response: $tokenResp ")
                Logger.d(TAG, "Token exception: $tokenEx ")
                Logger.d(TAG, "Token: ${authState?.accessToken} ")
            }
        }

        authState?.performActionWithFreshTokens(authService!!)
        { accessToken, idToken, refreshTokenEx ->
            if (refreshTokenEx != null) {
                // negotiation for fresh tokens failed, check ex for more details
                Logger.d(TAG, "Token exception: $refreshTokenEx ")
            } else {
                Logger.d(TAG, "accessToken: $accessToken ")
                Logger.d(TAG, "idToken: $idToken ")
            }
        }


    }

}