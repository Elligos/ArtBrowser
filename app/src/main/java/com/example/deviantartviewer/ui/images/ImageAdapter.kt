package com.example.deviantartviewer.ui.images

import android.view.ViewGroup
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.ui.base.BaseAdapter
import com.example.deviantartviewer.ui.base.BaseViewModel


class ImageAdapter (
        viewmodel: BaseViewModel,
        diffUtils: ImageDiffUtils
) : BaseAdapter<Image, ImageItemViewHolder>(viewmodel, diffUtils){

    var vm = viewmodel

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) :
            ImageItemViewHolder = ImageItemViewHolder(parent, vm)

}