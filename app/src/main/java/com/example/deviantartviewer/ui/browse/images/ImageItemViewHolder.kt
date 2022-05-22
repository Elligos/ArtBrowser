package com.example.deviantartviewer.ui.browse.images

import android.graphics.ColorFilter
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.deviantartviewer.R
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.databinding.ImageViewCardBinding
import com.example.deviantartviewer.ui.base.BaseItemViewHolder
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.utils.log.Logger


class ImageItemViewHolder (parent : ViewGroup, vm : BaseViewModel):
        BaseItemViewHolder<Image, BaseViewModel>(R.layout.image_view_card,parent,vm) {


    lateinit var binding: ImageViewCardBinding

    override fun setupView(view: View) {

        binding = ImageViewCardBinding.bind(view)

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
                .load(data.url)
                .timeout(3000)
                .placeholder(circularProgressDrawable)
        glideRequest.into(binding.ivImage)

        super.bind(data)
    }
}