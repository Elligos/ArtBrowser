package com.example.deviantartviewer.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel>() {

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
            findNavController().navigate(R.id.action_LoginFragment_to_profileFragment)

        }


        binding.textviewSignupMessage.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }
}