package com.example.deviantartviewer.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.deviantartviewer.R
import com.example.deviantartviewer.databinding.FragmentLoginBinding
import com.example.deviantartviewer.databinding.FragmentProfileBinding
import com.example.deviantartviewer.di.component.FragmentComponent
import com.example.deviantartviewer.ui.base.BaseFragment
import com.example.deviantartviewer.ui.login.LoginFragment
import com.example.deviantartviewer.ui.main.MainSharedViewModel
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.log.Logger
import javax.inject.Inject


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

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    override fun provideLayoutId(): Int   = R.layout.fragment_profile

    override fun setupView(view: View) {
        _binding = FragmentProfileBinding.bind(view)

        binding.ivLogoutIcon.setOnClickListener{
            viewModel.logout()
        }
    }

    override fun setupObservers(){
        super.setupObservers()
        
        viewModel.imageUrl.observe(this, {
            it?.run {
                val glideRequest = Glide
                    .with(binding.ivUserImage.context)
                    .asBitmap()
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(e: GlideException?,
                                                  model: Any?,
                                                  target: Target<Bitmap>?,
                                                  isFirstResource: Boolean): Boolean {
                            Logger.d(TAG, "Image icon loading failed!")
                            return false
                        }

                        override fun onResourceReady(resource: Bitmap?,
                                                     model: Any?,
                                                     target: Target<Bitmap>?,
                                                     dataSource: DataSource?,
                                                     isFirstResource: Boolean): Boolean {

                            if(resource!=null) viewModel.saveImageToInternalStorage(resource)
                            Logger.d(TAG, "Image icon loading completed!")
                            return false
                        }
                    })

                glideRequest.into(binding.ivUserImage)

            }
        })

        viewModel.imageUri.observe(this, {
            val bitmap = BitmapFactory.decodeFile(it)
            if(bitmap == null) Logger.d(TAG, "Bitmap is null!")

            else Glide.with(binding.ivUserImage.context)
                     .load(bitmap)
                     .apply(RequestOptions.circleCropTransform())
                     .into(binding.ivUserImage)

            Logger.d(TAG, "Loading imageicon from internal storage")
        })


        viewModel.userInfo.observe(this, {
            binding.tvUsernameProfile.text = it.username
            binding.tvProfileBirthday.text = it.age
            binding.tvProfileCountry.text = it.country
            binding.tvProfileViewsCounter.text = "${it.profilPageviews}"
            binding.tvWatchingYouCounter.text = "${it.watchers}"
            binding.tvYouWatchingCounter.text = "${it.friends}"
            binding.tvFavoritesCounter.text = "${it.userFavorites}"
            binding.tvCommentsMadeCounter.text = "${it.userComments}"
            binding.tvCommentsReceivedCounter.text = "${it.profileComments}"
        })

        viewModel.launchLogin.observe(this, {
            it.getIfNotHandled()?.run {
                findNavController().navigate(R.id.action_profileFragment_to_LoginFragment)
            }
        })

        viewModel.fetchInProcess.observe(this, {
            binding.pbLoading.visibility = if(it) View.VISIBLE else View.INVISIBLE
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}