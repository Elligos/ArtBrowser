package com.example.deviantartviewer.ui.browse

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
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
        private const val DEFAULT_FETCH_OFFSET = 0
        private const val MAX_FETCH_OFFSET = 50000
        private const val FETCH_LIMIT = 15//120
    }

    val images = ArrayList<Image>()
    var imagesReady : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    var newImagesResult : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    var fetchedImages = 0
    var maxImagesToFetch = 0
    var nextImagesFetchInProcess = false

    var currentQuery = ""
    var selectedItemPosition = 0


    override fun onCreate() {
        Logger.d(TAG, "BrowseViewModel created!")


        compositeDisposable.add(
                imageRepository.doNewestImagesFetch("", DEFAULT_FETCH_OFFSET, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Browse fetch newest request result: $it")
                                    fetchImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                    fetchedImages = FETCH_LIMIT
                                },
                                {
                                    Logger.d(TAG, "Browse fetch newest request failed with exception: $it")
                                }
                        )
        )
    }

    private fun fetchImagesFromResponse (response: ImageResponse){
        images.clear()
        maxImagesToFetch = response.estimatedTotal ?: MAX_FETCH_OFFSET

        for(result in response.results){
            if(result.isMature == true) continue

            val image = Image(  preview_url = result.preview?.src?:"",
                                content_url = result.content?.src?:"",
                                name = result.title ?: "",
                                author = result.author?.username ?: "",
                                isFavorite = result.isFavourited ?: false,
                                placeholderWidth = result.preview?.width ?: 0,
                                placeholderHeight = result.preview?.height ?: 0,
                                deviationid = result.deviationid ?: "" )

            if(image.preview_url != "") images.add(image)
        }
    }

    private fun addImagesFromResponse (response: ImageResponse){

        for(result in response.results){
            if(result.isMature == true) continue

            val image = Image(  preview_url = result.preview?.src?:"",
                                content_url = result.content?.src?:"",
                                name = result.title ?: "",
                                author = result.author?.username ?: "",
                                isFavorite = result.isFavourited ?: false,
                                placeholderWidth = result.preview?.width ?: 0,
                                placeholderHeight = result.preview?.height ?: 0,
                                deviationid = result.deviationid ?: "" )

            if(image.preview_url != "") images.add(image)
        }
    }


    fun loadNewImages(query : String){
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch(query, DEFAULT_FETCH_OFFSET, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Browse fetch newest request with query $query result: $it")
                                    currentQuery = query
                                    fetchImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                    newImagesResult.postValue(Event(emptyMap()))
                                    fetchedImages = FETCH_LIMIT
                                },
                                {
                                    Logger.d(TAG, "Browse fetch newest request with query $query failed with exception: $it")
                                }
                        )
        )
    }

    fun loadMoreImages(query : String){
        if(fetchedImages + FETCH_LIMIT >= maxImagesToFetch){
            nextImagesFetchInProcess = false
            return
        }
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch(query, offset = fetchedImages, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Browse fetch newest request for more images with query $query result: $it")
                                    addImagesFromResponse(it)
                                    imagesReady.postValue(Event(emptyMap()))
                                    nextImagesFetchInProcess = false
                                    fetchedImages += FETCH_LIMIT
                                },
                                {
                                    nextImagesFetchInProcess = false
                                    Logger.d(TAG, "Browse fetch newest request for more images with query $query failed with exception: $it")
                                }
                        )
        )
    }

    fun restoreFragmentState(){
        imagesReady.postValue(Event(emptyMap()))
    }


}