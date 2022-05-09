package com.example.deviantartviewer.ui.favorites

import android.view.View
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.databinding.FragmentFavoritesBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.BrowseViewModel

class FavoritesFragment : BaseFragment<FavoritesViewModel>()   {

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    //View Binding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_favorites

    override fun setupView(view: View) {
        _binding = FragmentFavoritesBinding.bind(view)
    }
}