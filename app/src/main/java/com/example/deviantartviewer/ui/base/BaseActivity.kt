package com.example.deviantartviewer.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.deviantartviewer.DeviantArtApp
import com.example.deviantartviewer.di.component.ActivityComponent
import com.example.deviantartviewer.di.module.ActivityModule
import com.example.deviantartviewer.di.component.DaggerActivityComponent
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    private fun buildActivityComponent() =
            DaggerActivityComponent
                    .builder()
                    .applicationComponent((application as DeviantArtApp).applicationComponent)
                    .activityModule(ActivityModule(this))
                    .build()

    protected open fun setupObservers() {
        viewModel.messageString.observe(this,  {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this,  {
            it.data?.run { showMessage(this) }
        })
    }

    fun showMessage(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))


    protected abstract fun provideLayoutId(): View

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setupView(savedInstanceState: Bundle?)
}