package com.example.deviantartviewer.ui.browse

import android.view.View
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.profile.ProfileViewModel

class BrowseFragment : BaseFragment<BrowseViewModel>()  {

    companion object {
        private const val TAG = "BrowseFragment"
    }

    //View Binding
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_browse

    override fun setupView(view: View) {
        _binding = FragmentBrowseBinding.bind(view)
    }
}