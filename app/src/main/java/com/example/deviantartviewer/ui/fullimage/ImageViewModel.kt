package com.example.deviantartviewer.ui.fullimage

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ImageViewModel (
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager,
        private val imageRepository: ImageRepository,
        private val userRepository: UserRepository
) : BaseViewModel(
        schedulerProvider, compositeDisposable, networkHelper, authManager
){

    companion object {
        private const val TAG = "ImageViewModel"
    }

    lateinit var image : Image
    val imageIsFavorite : MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate() {
        Logger.d(TAG, "ImageViewModel created!")
    }

    fun addToFavorite(deviationid : String){
        compositeDisposable.add(
                imageRepository.faveCall(deviationid)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Fave request result: $it")
                                    if(it.success) imageIsFavorite.postValue(true)
                                },
                                {
                                    Logger.d(TAG, "Fave request failed with exception: $it")
                                }
                        )
        )
    }

    fun removeFromFavorite(deviationid : String){
        compositeDisposable.add(
                imageRepository.unfaveCall(deviationid)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Unfave request result: $it")
                                    if(it.success) imageIsFavorite.postValue(false)
                                },
                                {
                                    Logger.d(TAG, "Unfave request failed with exception: $it")
                                }
                        )
        )
    }

    fun handleImageStatusChange(){
        Logger.d(TAG, "image.isFavorite = ${image.isFavorite}, " +
                "imageIsFavorite.value = ${imageIsFavorite.value}")
        if(image.isFavorite != imageIsFavorite.value) markUserInfoAsOutdated()
    }

    fun markUserInfoAsOutdated(){
        Logger.d(TAG, "Image favorite status changed from  ${image.isFavorite} " +
                            "to ${imageIsFavorite.value}")
        userRepository.setUserOutdated(true)
    }

}