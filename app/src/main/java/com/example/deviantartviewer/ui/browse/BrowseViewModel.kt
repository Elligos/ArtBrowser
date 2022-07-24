package com.example.deviantartviewer.ui.browse

import androidx.lifecycle.MutableLiveData
import com.example.deviantartviewer.data.authorization.AuthManager
import com.example.deviantartviewer.data.converter.Converter
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
        private const val FETCH_LIMIT = 120// Max amount of items to fetch is 120
    }

    val images = ArrayList<Image>()
    val allResponsesResults = ArrayList<ImageResponse.Results>()
    var imagesReady : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    var newImagesResult : MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    var fetchedImages = 0
    var maxImagesToFetch = 0
    var fetchInProcess : MutableLiveData<Boolean> = MutableLiveData(false)
    var newUploadedImagesAmount = 0

    var currentQuery = ""
    var selectedItemPosition = 0


    override fun onCreate() {
        Logger.d(TAG, "BrowseViewModel created!")
        loadInitialNewImages()
    }

    private fun loadInitialNewImages(){
        fetchInProcess.postValue(true)
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch("", DEFAULT_FETCH_OFFSET, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    handleInitialNewImagesResponse(it)
                                },
                                {
                                    handleInitialNewImagesResponseError(it)
                                }
                        )
        )
    }

    private fun handleInitialNewImagesResponse(response: ImageResponse){
        Logger.d(TAG, "Browse fetch newest request result: $response")
        allResponsesResults.clear()
        allResponsesResults.addAll(response.results)

        fetchInitialImagesFromResponse(response)
        fetchedImages = FETCH_LIMIT
        newUploadedImagesAmount = 0
        fetchInProcess.postValue(false)
        imagesReady.postValue(Event(emptyMap()))
    }

    private fun handleInitialNewImagesResponseError(error : Throwable){
        fetchInProcess.postValue(false)
        Logger.d(TAG, "Browse fetch newest request failed with exception: $error")
    }

    private fun fetchInitialImagesFromResponse (response: ImageResponse){
        images.clear()
        maxImagesToFetch = response.estimatedTotal ?: MAX_FETCH_OFFSET

        for(result in response.results){
            if(result.isMature == true) continue
            val image = Converter.convertToImage(result)
            if(image.preview_url != "") images.add(image)
        }
    }

    fun loadNewImages(query : String){
        fetchInProcess.postValue(true)
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch(query, DEFAULT_FETCH_OFFSET, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    handleNewImagesResponse(query, it)
                                },
                                {
                                    handleNewImagesResponseError(query, it)
                                }
                        )
        )
    }

    private fun handleNewImagesResponse(query : String, response : ImageResponse){
        Logger.d(TAG, "Browse fetch newest request with query $query result: $response")
        allResponsesResults.clear()
        allResponsesResults.addAll(response.results)

        currentQuery = query

        fetchInitialImagesFromResponse(response)
        fetchInProcess.postValue(false)
        imagesReady.postValue(Event(emptyMap()))
        newImagesResult.postValue(Event(emptyMap()))
        fetchedImages = FETCH_LIMIT
        newUploadedImagesAmount = 0
    }

    private fun handleNewImagesResponseError(query : String, error : Throwable){
        fetchInProcess.postValue(false)
        Logger.d(TAG, "Browse fetch newest request with query $query failed with exception: $error")
    }

    fun loadMoreImages(query : String){
        if(fetchedImages + FETCH_LIMIT >= maxImagesToFetch) return

        fetchInProcess.postValue(true)

        val offset = fetchedImages+newUploadedImagesAmount
        compositeDisposable.add(
                imageRepository.doNewestImagesFetch(query, offset, FETCH_LIMIT)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                                {
                                    handleMoreImagesResponse(query,it)
                                },
                                {
                                    handleMoreImagesResponseError(query, it)
                                }
                        )
        )
    }

    private fun handleMoreImagesResponse(query : String, response : ImageResponse){
        Logger.d(TAG, "Browse fetch newest request for more images " +
                    "with query $query result: $response")

        val duplicatesAmount = findAmountOfDuplicatesInResponse(response.results)
        allResponsesResults.addAll(response.results)

        addImagesFromResponse(response, amountToSkip = duplicatesAmount)
        newUploadedImagesAmount += duplicatesAmount
        fetchedImages += FETCH_LIMIT
        fetchInProcess.postValue(false)
        imagesReady.postValue(Event(emptyMap()))

        Logger.d(TAG, "Found $duplicatesAmount duplicates in browse fetch newest request ")
        Logger.d(TAG, "Found $newUploadedImagesAmount new uploaded images")
    }

    private fun handleMoreImagesResponseError(query : String, error: Throwable){
        fetchInProcess.postValue(false)
        Logger.d(TAG, "Browse fetch newest request for more images with query $query failed " +
                    "with exception: $error")
    }

    private fun addImagesFromResponse (response: ImageResponse, amountToSkip : Int){
        var imagesToSkip = amountToSkip
        if(imagesToSkip >= response.results.size){
            Logger.d(TAG, "Amount of duplicates more than amount of data in response !")
            return
        }

        for(result in response.results){
            if(result.isMature == true) continue
            if(imagesToSkip > 0){
                imagesToSkip--
                continue
            }

            val image = Converter.convertToImage(result)
            if(image.preview_url != "") images.add(image)
        }
    }


    private fun findAmountOfDuplicatesInResponse(newResponseResults : List<ImageResponse.Results>) : Int {

        val amountToCheck = allResponsesResults.size
        if(amountToCheck == 0) return 0
        val firstNewResult = newResponseResults[0]

        for( i in amountToCheck-1 downTo 0){
            if(allResponsesResults[i].deviationid == firstNewResult.deviationid)
                return allResponsesResults.size-i
        }

        return 0
    }

    fun restoreFragmentState(){
        imagesReady.postValue(Event(emptyMap()))
    }

}