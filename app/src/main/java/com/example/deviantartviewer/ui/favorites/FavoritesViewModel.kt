package com.example.deviantartviewer.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.CollectionsAllResponse
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
import com.example.deviantartviewer.ui.browse.BrowseViewModel
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
        private const val FETCH_LIMIT = 10
    }

    val images = ArrayList<Image>()
    var imagesReady : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    var newImagesResult : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    var selectedItemPosition = 0

    var totalFavorites= 0
    var fetchedImages = 0
    var nextImagesFetchInProcess = false
    var hasMoreToFetch = false

    override fun onCreate() {
        Logger.d(TAG, "FavoritesViewModel created!")


        compositeDisposable.add(
                imageRepository.doCollectionsAllFetch(0, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Fetch collections all request result: $it")
                                    fetchImagesFromResponse(it)
                                    nextImagesFetchInProcess = false
                                    fetchedImages += it.results.size
                                    hasMoreToFetch = it.hasMore ?: false
                                    imagesReady.postValue(Event(emptyMap()))
                                },
                                {
                                    Logger.d(TAG, "Fetch collections all request failed with exception: $it")
                                }
                        )
        )
    }

    private fun fetchImagesFromResponse (response: CollectionsAllResponse){

        for(result in response.results){
            val image = Image(  url = result.preview?.src?:"",
                                name = result.title ?: "",
                                author = result.author?.username ?: "",
                                isFavorite = result.isFavourited ?: false,
                                placeholderWidth = result.preview?.width ?: 0,
                                placeholderHeight = result.preview?.height ?: 0,
                                deviationid = result.deviationid ?: "" )

            images.add(image)
        }
    }

    fun loadMoreFavorites(){

        if( !hasMoreToFetch ){
            nextImagesFetchInProcess = false
            return
        }

        compositeDisposable.add(
                imageRepository.doCollectionsAllFetch(offset = fetchedImages, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    Logger.d(TAG, "Fetch request for more favorite images result: $it")
                                    addImagesFromResponse(it)
                                    nextImagesFetchInProcess = false
                                    fetchedImages += it.results.size
                                    hasMoreToFetch = it.hasMore ?: false
                                    imagesReady.postValue(Event(emptyMap()))

                                },
                                {
                                    nextImagesFetchInProcess = false
                                    Logger.d(TAG, "Fetch request for more favorite images failed with exception: $it")
                                }
                        )
        )
    }

    private fun addImagesFromResponse (response: CollectionsAllResponse){

        for(result in response.results){
            if(result.isMature == true) continue

            val image = Image(  url = result.preview?.src?:"",
                    name = result.title ?: "",
                    author = result.author?.username ?: "",
                    isFavorite = result.isFavourited ?: false,
                    placeholderWidth = result.preview?.width ?: 0,
                    placeholderHeight = result.preview?.height ?: 0,
                    deviationid = result.deviationid ?: "" )

            if(image.url != "") images.add(image)
        }
    }

    fun restoreFragmentState(){
        imagesReady.postValue(Event(emptyMap()))
    }


}