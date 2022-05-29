package com.example.deviantartviewer.ui.browse

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.data.repository.UserRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.profile.ProfileViewModel
import com.example.deviantartviewer.utils.common.Event
import com.example.deviantartviewer.utils.log.Logger
import com.example.deviantartviewer.utils.network.NetworkHelper
import com.example.deviantartviewer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class BrowseViewModel (
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        authManager: AuthManager,
        private val imageRepository: ImageRepository
) : BaseViewModel(
        schedulerProvider, compositeDisposable, networkHelper, authManager
){

    companion object {
        private const val TAG = "BrowseViewModel"
    }

    val images = ArrayList<Image>()
    var imagesReady : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    override fun onCreate() {
        Logger.d(TAG, "BrowseViewModel created!")


        compositeDisposable.add(
                imageRepository.doNewestImagesFetch("")
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Browse fetch newest request result: $it")
                                    fetchImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                },
                                {
                                    Logger.d(TAG, "Browse fetch newest request failed with exception: $it")
                                }
                        )
        )
    }

    private fun fetchImagesFromResponse (response: ImageResponse){
        images.clear()
        for(result in response.results){
            val image = Image( result.preview?.src?:"",
                             result.title ?: "",
                    result.preview?.width ?: 0,
                    result.preview?.height ?: 0 )
            if(image.url != "") images.add(image)
        }
    }

    fun loadNewImages(query : String){
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch(query)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Browse fetch newest request with query $query result: $it")
                                    fetchImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                },
                                {
                                    Logger.d(TAG, "Browse fetch newest request with query $query failed with exception: $it")
                                }
                        )
        )
    }


}