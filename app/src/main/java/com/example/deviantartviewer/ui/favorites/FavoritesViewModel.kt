package com.example.deviantartviewer.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.converter.Converter
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.CollectionsAllResponse
import com.example.deviantartviewer.data.repository.ImageRepository
import com.example.deviantartviewer.ui.base.BaseViewModel
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

    var selectedItemPosition = 0

    var fetchedImages = 0
    var fetchInProcess : MutableLiveData<Boolean> = MutableLiveData()
    var hasMoreToFetch = false

    override fun onCreate() {
        Logger.d(TAG, "FavoritesViewModel created!")


        compositeDisposable.add(
                imageRepository.doCollectionsAllFetch(0, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    handleInitialFavoritesResponse(it)
                                },
                                {
                                    handleInitialFavoritesResponseError(it)
                                }
                        )
        )
    }

    private fun handleInitialFavoritesResponse(response: CollectionsAllResponse){
        Logger.d(TAG, "Fetch collections all request result: $response")
        fetchImagesFromResponse(response)
        fetchInProcess.postValue(false)
        fetchedImages += response.results.size
        hasMoreToFetch = response.hasMore ?: false
        imagesReady.postValue(Event(emptyMap()))
    }

    private fun handleInitialFavoritesResponseError(error : Throwable){
        Logger.d(TAG, "Fetch collections all request failed with exception: $error")
    }

    private fun fetchImagesFromResponse (response: CollectionsAllResponse){

        for(result in response.results){
            val image = Converter.convertToImage(result)
            images.add(image)
        }
    }

    fun loadMoreFavorites(){

        if( !hasMoreToFetch ) return

        fetchInProcess.postValue(true)
        compositeDisposable.add(
                imageRepository.doCollectionsAllFetch(offset = fetchedImages, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    handleMoreFavoritesResponse(it)
                                },
                                {
                                    handleMoreFavoritesResponseError(it)
                                }
                        )
        )
    }

    private fun handleMoreFavoritesResponse(response: CollectionsAllResponse){
        Logger.d(TAG, "Fetch request for more favorite images result: $response")
        addImagesFromResponse(response)
        fetchedImages += response.results.size
        hasMoreToFetch = response.hasMore ?: false
        fetchInProcess.postValue(false)
        imagesReady.postValue(Event(emptyMap()))
    }

    private fun handleMoreFavoritesResponseError(error: Throwable){
        fetchInProcess.postValue(false)
        Logger.d(TAG, "Fetch request for more favorite images failed with exception: $error")
    }

    private fun addImagesFromResponse (response: CollectionsAllResponse){

        for(result in response.results){
            if(result.isMature == true) continue

            val image = Converter.convertToImage(result)
            if(image.preview_url != "") images.add(image)
        }
    }

    fun restoreFragmentState(){
        imagesReady.postValue(Event(emptyMap()))
    }


}