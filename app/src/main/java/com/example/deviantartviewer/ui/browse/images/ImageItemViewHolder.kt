package com.example.deviantartviewer.ui.browse.images

import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
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

//    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
//        viewHolderComponent.inject(this)
//    }

    override fun setupView(view: View) {

        binding = ImageViewCardBinding.bind(view)

        with(binding){
            tvImageName.text = "name"


        }

//        binding.ivPost.setOnClickListener {
//            ImageAdapter.RxBus.itemClickStream.onNext(viewModel.data.value!!.url)
//        }
    }

    override fun setupObservers() {
        super.setupObservers()

//        viewModel.imageDetail.observe(this, {
//            it?.run {
//                val glideRequest = Glide
//                        .with(binding.ivPost.context)
//                        .load(url)
//
//                glideRequest.into(binding.ivPost)
//
//            }
//        })
    }

    override fun bind(data: Image) {
        binding.tvImageName.text = data.name
        Logger.d("ImageItemViewHolder", "onActivityResult data is null!")

        val circularProgressDrawable = CircularProgressDrawable(binding.ivImage.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        val glideRequest = Glide
                .with(binding.ivImage.context)
                .load(data.url)
                .placeholder(circularProgressDrawable)
        glideRequest.into(binding.ivImage)

        super.bind(data)
    }
}