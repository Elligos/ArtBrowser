package com.example.deviantartviewer.ui.browse.images

import androidx.recyclerview.widget.DiffUtil
import com.example.deviantartviewer.data.model.Image

class ImageDiffUtils : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.preview_url == newItem.preview_url
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}