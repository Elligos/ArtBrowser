package com.example.deviantartviewer.ui.browse

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.images.ImageAdapter
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.log.Logger
import javax.inject.Inject

class BrowseFragment : BaseFragment<BrowseViewModel>()  {

    companion object {
        private const val TAG = "BrowseFragment"
        private const val SPAN_COUNT =2
    }

    //View Binding
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    lateinit var imageAdapter: ImageAdapter
    var gridLayoutManager = GridLayoutManager(this.context, SPAN_COUNT)


    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_browse

    override fun setupView(view: View) {
        _binding = FragmentBrowseBinding.bind(view)

        imageAdapter = ImageAdapter(viewModel.images, viewModel)

        binding.rvBrowsedImages.apply {
            layoutManager = gridLayoutManager
            adapter = imageAdapter
        }//.addItemDecoration(gridSpacingItemDecoration)

    }

    override fun setupObservers(){
        super.setupObservers()

        viewModel.imagesReady.observe(this, {
            it.getIfNotHandled()?.run {
                imageAdapter.updateData()
                Logger.d(TAG, "BrowseFragment: append images data to the adapter")
            }
        })



    }


}