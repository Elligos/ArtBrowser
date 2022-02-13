package com.example.deviantartviewer.ui.profile

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.profile.ProfileViewModel



class ProfileFragment : BaseFragment<ProfileViewModel>() {

    //View Binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_profile

    override fun setupView(view: View) {
        _binding = FragmentProfileBinding.bind(view)


    }
}