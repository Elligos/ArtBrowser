package com.example.deviantartviewer.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.browse.BrowseViewModel
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class FavoritesViewModel (
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager,
        private val imageRepository: ImageRepository
) : BaseViewModel(
        schedulerProvider, compositeDisposable, networkHelper, authManager
){

    companion object {
        private const val TAG = "FavoritesViewModel"
    }

    val images = ArrayList<Image>()
    var imagesReady : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    override fun onCreate() {
        Logger.d(TAG, "FavoritesViewModel created!")


        compositeDisposable.add(
                imageRepository.doCollectionsAllFetch()
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Fetch collections all request result: $it")
                                    fetchImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                },
                                {
                                    Logger.d(TAG, "Fetch collections all request failed with exception: $it")
                                }
                        )
        )
    }

    private fun fetchImagesFromResponse (response: ImageResponse){

        for(result in response.results){
            val image = Image( result.preview?.src?:"",
                    result.title ?: "",
                    result.preview?.width ?: 0,
                    result.preview?.height ?: 0 )
            images.add(image)
        }
    }


}