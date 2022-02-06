package com.example.deviantartviewer.ui.signup

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentSignUpBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment

class SignupFragment : BaseFragment<SignupViewModel>() {

    //View Binding
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_sign_up

    override fun setupView(view: View) {
        _binding = FragmentSignUpBinding.bind(view)

        binding.etUsernameSignUp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onUsernameChange(s.toString())
            }

        })

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


        binding.buttonSecond.setOnClickListener{
            viewModel.doSignup()
            showMessage("Sign up with email: ${viewModel.emailField.value}, " +
                    "pswd: ${viewModel.passwordField.value}, " +
                    "and name: ${viewModel.usernameField.value} !")

        }


        binding.textviewSignInMessage.setOnClickListener{
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }


}