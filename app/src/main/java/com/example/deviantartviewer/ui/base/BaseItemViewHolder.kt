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



//    private fun buildViewHolderComponent() =
//            DaggerViewHolderComponent
//                    .builder()
//                    .applicationComponent((itemView.context.applicationContext as DeviantArtApp).applicationComponent)
//                    .viewHolderModule(ViewHolderModule(this))
//                    .build()

    //fun showMessage(message: String) = Toaster.show(itemView.context, message)

    //fun showMessage(@StringRes resId: Int) = showMessage(itemView.context.getString(resId))

    protected open fun setupObservers() {

    }

    //protected abstract fun injectDependencies(viewHolderComponent: ViewHolderComponent)

    abstract fun setupView(view: View)


}