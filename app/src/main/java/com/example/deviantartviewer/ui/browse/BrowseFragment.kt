package com.example.deviantartviewer.ui.browse

import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.images.ImageAdapter
import com.example.deviantartviewer.ui.browse.images.ImageDiffUtils
import com.example.deviantartviewer.utils.log.Logger

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
    var diffUtilsCallback = ImageDiffUtils()


    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_browse

    override fun setupView(view: View) {
        _binding = FragmentBrowseBinding.bind(view)

        imageAdapter = ImageAdapter(/*viewModel.images, */viewModel, diffUtilsCallback)
        imageAdapter.setOnItemClickListener {
            Logger.d(TAG, "Image \"${it.name}\" clicked!")
        }

        binding.rvBrowsedImages.apply {
            layoutManager = gridLayoutManager
            adapter = imageAdapter
        }

        binding.svBrowseImages.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svBrowseImages.clearFocus()

                viewModel.loadNewImages(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun setupObservers(){
        super.setupObservers()

        viewModel.imagesReady.observe(this, {
            it.getIfNotHandled()?.run {
                imageAdapter.updateData(viewModel.images)
                Logger.d(TAG, "BrowseFragment: append images data to the adapter")
            }
        })



    }
}

