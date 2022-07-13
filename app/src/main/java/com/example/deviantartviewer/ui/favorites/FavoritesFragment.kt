package com.example.deviantartviewer.ui.favorites

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
import com.example.deviantartviewer.ui.main.MainSharedViewModel
import com.example.deviantartviewer.utils.log.Logger
import javax.inject.Inject

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

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_favorites

    override fun setupView(view: View) {
        _binding = FragmentFavoritesBinding.bind(view)

        imageAdapter = ImageAdapter(viewModel, diffUtilsCallback)

        imageAdapter.setOnItemClickListener {image, position ->
            Logger.d(TAG, "Image \"${image}\" with position $viewModel.selectedItemPosition clicked!")
            mainSharedViewModel.selectedImage.value = image
            viewModel.selectedItemPosition = position
            findNavController().navigate(R.id.action_FavoritesFragment_to_ImageFragment)
        }
        imageAdapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY

        binding.rvFavoriteImages.apply {
            if(layoutManager==null) layoutManager = gridLayoutManager
            if(adapter==null) adapter = imageAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    onEndOfListListener(dy)
                }
            })
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

        mainSharedViewModel.backFromImageScreen.observe(this, {
            it.getIfNotHandled()?.run{
                if(mainSharedViewModel.selectedImage.value?.isFavorite == false){
                    viewModel.images.removeAt(viewModel.selectedItemPosition)
                    viewModel.fetchedImages = viewModel.images.size
                    mainSharedViewModel.selectedImage.value = null
                }
                viewModel.restoreFragmentState()
            }


        })

    }

    fun onEndOfListListener(dy : Int){
        if (dy <= 0) return
        if (viewModel.nextImagesFetchInProcess) return

        val visibleItemCount = binding.rvFavoriteImages.childCount
        val totalItemCount = binding.rvFavoriteImages.layoutManager!!.itemCount
        val firstVisibleItem =
            (binding.rvFavoriteImages.layoutManager as GridLayoutManager)
                .findFirstVisibleItemPosition()

        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
            viewModel.nextImagesFetchInProcess = true
            viewModel.loadMoreFavorites()
        }
    }
}