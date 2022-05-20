package com.example.deviantartviewer.ui.browse.images

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.ui.base.BaseAdapter
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.browse.BrowseViewModel
import javax.inject.Inject


class ImageAdapter (
        images: ArrayList<Image>,
        viewmodel: BaseViewModel
) : BaseAdapter<Image, ImageItemViewHolder>(images, viewmodel){



//    @Inject
//    lateinit var vm : BrowseViewModel

    var vm = viewmodel


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) :
            ImageItemViewHolder = ImageItemViewHolder(parent, vm)

}