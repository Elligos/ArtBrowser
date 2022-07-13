package com.example.deviantartviewer.ui.browse.images

import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.databinding.ImagePreviewCardBinding
import com.example.deviantartviewer.ui.base.BaseItemViewHolder
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.log.Logger


class ImageItemViewHolder (parent : ViewGroup, vm : BaseViewModel):
        BaseItemViewHolder<Image, BaseViewModel>(R.layout.image_preview_card,parent,vm) {


    lateinit var binding: ImagePreviewCardBinding

    override fun setupView(view: View) {

        binding = ImagePreviewCardBinding.bind(view)

    }

     override fun setupObservers() {
        super.setupObservers()
    }

    override fun bind(data: Image) {
        binding.tvImageName.text = data.name
        Logger.d("ImageItemViewHolder", "onActivityResult data is null!")

        val circularProgressDrawable = CircularProgressDrawable(binding.ivImage.context)
        circularProgressDrawable.apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(R.color.devArtGreen)
        }.start()

        val glideRequest = Glide
                .with(binding.ivImage.context)
                .load(data.preview_url)
                .timeout(3000)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_baseline_image_not_supported_24_green)
        glideRequest.into(binding.ivImage)

        super.bind(data)
    }
}