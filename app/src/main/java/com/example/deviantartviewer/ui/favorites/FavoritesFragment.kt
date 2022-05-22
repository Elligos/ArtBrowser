package com.example.deviantartviewer.ui.favorites

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.databinding.FragmentFavoritesBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.BrowseFragment
import com.example.deviantartviewer.ui.browse.BrowseViewModel
import com.example.deviantartviewer.ui.browse.images.ImageAdapter
import com.example.deviantartviewer.ui.browse.images.ImageDiffUtils
import com.example.deviantartviewer.utils.log.Logger

class FavoritesFragment : BaseFragment<FavoritesViewModel>()   {

    companion object {
        private const val TAG = "FavoritesFragment"
        private const val SPAN_COUNT =2
    }

    //View Binding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    lateinit var imageAdapter: ImageAdapter
    var gridLayoutManager = GridLayoutManager(this.context, SPAN_COUNT)
    var diffUtilsCallback = ImageDiffUtils()

    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_favorites

    override fun setupView(view: View) {
        _binding = FragmentFavoritesBinding.bind(view)

        imageAdapter = ImageAdapter(/*viewModel.images,*/ viewModel, diffUtilsCallback)

        binding.rvFavoriteImages.apply {
            layoutManager = gridLayoutManager
            adapter = imageAdapter
        }
    }

    override fun setupObservers(){
        super.setupObservers()

        viewModel.imagesReady.observe(this, {
            it.getIfNotHandled()?.run {
                imageAdapter.updateData(viewModel.images)
                Logger.d(TAG, "FavoritesFragment: append images data to the adapter")
            }
        })



    }
}