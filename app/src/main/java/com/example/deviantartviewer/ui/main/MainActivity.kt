package com.example.deviantartviewer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.deviantartviewer.R
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.deviantartviewer.databinding.ActivityMainBinding
import com.example.deviantartviewer.di.component.ActivityComponent
import com.example.deviantartviewer.ui.base.BaseActivity
import com.example.deviantartviewer.utils.log.Logger
import com.jakewharton.retrofit2.adapter.rxjava2.Result.response
import net.openid.appauth.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController


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

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

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

//        when(destination.id){
//            R.id.BrowseFragment -> binding.toolbar.visibility = View.VISIBLE
//            else -> binding.toolbar.visibility = View.GONE
//        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_browse, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }



}