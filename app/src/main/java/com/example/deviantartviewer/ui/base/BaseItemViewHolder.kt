package com.example.deviantartviewer.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.di.component.ViewHolderComponent
import com.example.deviantartviewer.di.module.ViewHolderModule

abstract class BaseItemViewHolder<T : Any, VM: BaseViewModel>( @LayoutRes layoutId: Int,
                                            parent: ViewGroup, viewModel : VM) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)){

    init {
        onCreate()
    }



    open fun bind(data: T) {
        //viewModel.updateData(data)
    }

    protected fun onCreate() {
 //       injectDependencies(buildViewHolderComponent())
        setupObservers()
        setupView(itemView)
    }


    protected open fun setupObservers() {
    }


    abstract fun setupView(view: View)


}