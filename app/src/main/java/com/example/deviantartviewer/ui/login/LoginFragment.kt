package com.example.deviantartviewer.ui.login

import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.utils.log.Logger
import net.openid.appauth.*


class LoginFragment : BaseFragment<LoginViewModel>() {

    companion object {
        private const val TAG = "LoginFragment"
        private const val AUTH_REQUEST_CODE = 19777
    }

    //View Binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_login

    override fun setupView(view: View) {
        _binding = FragmentLoginBinding.bind(view)


        binding.buttonLogin.setOnClickListener{
            startActivityForResult(viewModel.getAuthIntent(), AUTH_REQUEST_CODE)
        }

    }

    override fun setupObservers(){
        super.setupObservers()
        viewModel.launchMain.observe(this, {
            it.getIfNotHandled()?.run {
                findNavController().navigate(R.id.action_LoginFragment_to_profileFragment)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data == null) {
            Logger.d(TAG, "onActivityResult data is null!")
            return
        }
        Logger.d(TAG, "Request code : $requestCode ")
        if (requestCode == AUTH_REQUEST_CODE){
            Logger.d(TAG, "Auth response")
            viewModel.requestTokenWithData(data)
        }

    }

}