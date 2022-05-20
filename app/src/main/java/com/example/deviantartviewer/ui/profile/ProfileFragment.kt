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
            viewModel.logout()
        }
    }

    override fun setupObservers(){
        super.setupObservers()

        //TODO: load big-icon instead usual
        //url example for big icon: https://a.deviantart.net/avatars-big/d/m/dmitriydev.jpg?10
        //url example for usual icon: https://a.deviantart.net/avatars/d/m/dmitriydev.jpg?10
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

}