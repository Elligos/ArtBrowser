package com.example.deviantartviewer.ui.fullimage

import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentImageBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.main.MainSharedViewModel
import com.example.deviantartviewer.utils.common.Event
import javax.inject.Inject

class ImageFragment : BaseFragment<ImageViewModel>()  {

    companion object {
        private const val TAG = "ImageFragment"
        private const val SPAN_COUNT =2
    }

    //View Binding
    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_image

    override fun setupView(view: View) {
        _binding = FragmentImageBinding.bind(view)

        with(binding){
            ivBackArrow.setOnClickListener {
                backUpToPreviousScreen()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this){
            backUpToPreviousScreen()
        }

        binding.ivAddToFavorite.setOnClickListener{
            if(viewModel.imageIsFavorite.value == true){
                viewModel.removeFromFavorite(viewModel.image.deviationid)
            }
            else{
                viewModel.addToFavorite(viewModel.image.deviationid)
            }
        }


    }

    override fun setupObservers(){
        super.setupObservers()

        mainSharedViewModel.selectedImage.observe(this,  {
            viewModel.image = it
            viewModel.imageIsFavorite.postValue(it.isFavorite)

            binding.tvTitle.text = it.name
            binding.tvAuthor.text = it.author

            val circularProgressDrawable = CircularProgressDrawable(binding.ivFullImage.context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                setColorSchemeColors(R.color.devArtGreen)
            }.start()

            val glideRequest = Glide
                    .with(binding.ivFullImage.context)
                    .load(it.url)
                    .timeout(3000)
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_baseline_image_not_supported_24_green)
            glideRequest.into(binding.ivFullImage)
        })


        viewModel.imageIsFavorite.observe(this, { favorite ->
            if(favorite){
                binding
                .ivAddToFavorite
                .setImageResource(R.drawable.ic_baseline_star_24_green)
                mainSharedViewModel.selectedImage.value?.isFavorite = true
            }
            else{
                binding
                .ivAddToFavorite
                .setImageResource(R.drawable.ic_baseline_star_border_24_green)
                mainSharedViewModel.selectedImage.value?.isFavorite = false
            }
        })
    }

    fun backUpToPreviousScreen(){
        mainSharedViewModel.backFromImageScreen.postValue(Event(true))
        findNavController().navigateUp()
    }
}