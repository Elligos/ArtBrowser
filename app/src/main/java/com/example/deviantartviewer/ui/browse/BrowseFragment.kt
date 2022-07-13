package com.example.deviantartviewer.ui.browse

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentBrowseBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.browse.images.ImageAdapter
import com.example.deviantartviewer.ui.browse.images.ImageDiffUtils
import com.example.deviantartviewer.ui.main.MainSharedViewModel
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
    var diffUtilsCallback = ImageDiffUtils()




    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_browse

    override fun setupView(view: View) {
        _binding = FragmentBrowseBinding.bind(view)

        imageAdapter = ImageAdapter(viewModel, diffUtilsCallback)
        imageAdapter.setOnItemClickListener {image, position ->
            Logger.d(TAG, "Image \"${image}\" with position ${viewModel.selectedItemPosition} clicked!")
            mainSharedViewModel.selectedImage.value = image
            viewModel.selectedItemPosition = position
            findNavController().navigate(R.id.action_BrowseFragment_to_ImageFragment)
        }
        imageAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY


        binding.rvBrowsedImages.apply {
            if(layoutManager==null) layoutManager = gridLayoutManager
            if(adapter==null) adapter = imageAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    onEndOfListListener(dy)
                }
            })
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

        Logger.d(TAG, "Image adapter list size is ${imageAdapter.differ.currentList.size} ")

    }

    override fun setupObservers(){
        super.setupObservers()

        viewModel.imagesReady.observe(this, {
            it.getIfNotHandled()?.run {
                imageAdapter.updateData(viewModel.images)
                Logger.d(TAG, "BrowseFragment: append images data to the adapter")
            }
        })

        viewModel.newImagesResult.observe(this, {
            binding.rvBrowsedImages.scrollToPosition(0)
        })

        mainSharedViewModel.backFromImageScreen.observe(this, {

            it.getIfNotHandled()?.run{
                mainSharedViewModel.selectedImage.value?.let { image ->
                    viewModel.images[viewModel.selectedItemPosition] = image
                    mainSharedViewModel.selectedImage.value = null
                }
                viewModel.restoreFragmentState()
            }
        })



    }

    fun onEndOfListListener(dy : Int){
        if (dy <= 0)  return //check for scroll down
        if (viewModel.nextImagesFetchInProcess) return

        val visibleItemCount = binding.rvBrowsedImages.childCount
        val totalItemCount = binding.rvBrowsedImages.layoutManager!!.itemCount
        val firstVisibleItem =
            (binding.rvBrowsedImages.layoutManager as GridLayoutManager)
                .findFirstVisibleItemPosition()

        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
            viewModel.nextImagesFetchInProcess = true
            viewModel.loadMoreImages(viewModel.currentQuery)
        }

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable("layout_key", gridLayoutManager.onSaveInstanceState())
//    }

    override fun onAttach(context: Context) {
        Logger.d(TAG, "BrowseFragment: onAttach()")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.d(TAG, "BrowseFragment: onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.d(TAG, "BrowseFragment: onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        Logger.d(TAG, "BrowseFragment: onStart()")
        super.onStart()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Logger.d(TAG, "BrowseFragment: onViewStateRestored()")
        super.onViewStateRestored(savedInstanceState)
    }


    override fun onResume() {
        Logger.d(TAG, "BrowseFragment: onResume() /n " +
                "selectedItemPosition = $viewModel.selectedItemPosition")
        super.onResume()
    }

    override fun onPause() {
        Logger.d(TAG, "BrowseFragment: onPause()")
        super.onPause()
    }

    override fun onStop() {
        Logger.d(TAG, "BrowseFragment: onStop()")
        super.onStop()
    }

    override fun onDestroyView() {
        Logger.d(TAG, "BrowseFragment: onDestroyView()")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Logger.d(TAG, "BrowseFragment: onDestroy()")
        super.onDestroy()
    }

    override fun onDetach() {
        Logger.d(TAG, "BrowseFragment: onDetach()")
        super.onDetach()
    }

}

