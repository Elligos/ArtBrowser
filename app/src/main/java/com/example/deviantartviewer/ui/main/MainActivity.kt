package com.example.deviantartviewer.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androidadvance.topsnackbar.TSnackbar
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.ActivityMainBinding
import com.example.deviantartviewer.di.component.ActivityComponent
import com.example.deviantartviewer.ui.base.BaseActivity


class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController
    lateinit var  networkErrorSnackbar : TSnackbar

    override fun provideLayoutId(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)


    //  Navigation.findNavController relies on findViewById(), which only works after you've called
    // setContentView() - i.e., actually added your Views to your activity.
    // That is why we call setContentView() before setupView() in BaseActivity->onCreate()
    override fun setupView(savedInstanceState: Bundle?) {

        navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged(destination)
        }

        setupSnackbar()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.getNetworkConnected().observe(this, {
            if (it == true) networkErrorSnackbar.dismiss()
            else networkErrorSnackbar.show()
        })
    }

    private fun onDestinationChanged(destination: NavDestination) {

        when(destination.id){
            R.id.BrowseFragment,
            R.id.FavoritesFragment,
            R.id.profileFragment -> {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            else -> {
                binding.bottomNavigation.visibility = View.GONE
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    private fun setupSnackbar(){
        networkErrorSnackbar = TSnackbar
                .make(binding.clSnackbar, "OOPS, NO INTERNET CONNECTION !", TSnackbar.LENGTH_INDEFINITE)
        networkErrorSnackbar.setIconRight(R.drawable.ic_baseline_wifi_off_24_white, 24f)
        val snackbarView = networkErrorSnackbar.view
        val textView = snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
    }

}