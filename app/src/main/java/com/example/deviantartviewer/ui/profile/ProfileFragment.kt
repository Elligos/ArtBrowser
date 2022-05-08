package com.example.deviantartviewer.ui.profile

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.log.Logger


class ProfileFragment : BaseFragment<ProfileViewModel>() {

    companion object {
        private const val TAG = "ProfileFragment"
        private const val LOGOUT_REQUEST_CODE = 98232
    }

    //View Binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    //Dependency injection
    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun provideLayoutId(): Int   = R.layout.fragment_profile

    override fun setupView(view: View) {
        _binding = FragmentProfileBinding.bind(view)

        binding.ivLogoutIcon.setOnClickListener{
//            startActivityForResult(viewModel.getLogoutIntent(), LOGOUT_REQUEST_CODE)
            viewModel.logout()
        }
    }

    override fun setupObservers(){
        super.setupObservers()

        viewModel.imageUrl.observe(this, {
            it?.run {
                val glideRequest = Glide
                    .with(binding.ivUserImage.context)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())

                glideRequest.into(binding.ivUserImage)
            }
        })

        viewModel.username.observe(this, {
            binding.tvUsernameProfile.text = it
        })

        viewModel.birthday.observe(this, {
            binding.tvProfileBirthday.text = it
        })

        viewModel.country.observe(this, {
            binding.tvProfileCountry.text = it
        })

        viewModel.profileViews.observe(this, {
            binding.tvProfileViewsCounter.text = it
        })

        viewModel.watchingYou.observe(this, {
            binding.tvWatchingYouCounter.text = it
        })

        viewModel.youWatching.observe(this, {
            binding.tvYouWatchingCounter.text = it
        })

        viewModel.favorites.observe(this, {
            binding.tvFavoritesCounter.text = it
        })

        viewModel.commentsMade.observe(this, {
            binding.tvCommentsMadeCounter.text = it
        })

        viewModel.commentsReceived.observe(this, {
            binding.tvCommentsReceivedCounter.text = it
        })

        viewModel.launchLogin.observe(this, {
            it.getIfNotHandled()?.run {
                findNavController().navigate(R.id.action_profileFragment_to_LoginFragment)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data == null) {
            Logger.d(TAG, "onActivityResult data is null!")
            return
        }
        Logger.d(TAG, "Request code : $requestCode ")
        if (requestCode == LOGOUT_REQUEST_CODE){
            Logger.d(TAG, "Logout response")
            viewModel.logout()
        }

    }
}