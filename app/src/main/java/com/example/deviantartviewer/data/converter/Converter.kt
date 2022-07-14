package com.example.deviantartviewer.data.converter

import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.remote.response.CollectionsAllResponse
import com.example.deviantartviewer.data.remote.response.ImageResponse

object Converter {

    fun convertToImage(result : ImageResponse.Results) : Image {
        return Image(  preview_url = result.preview?.src?:"",
                content_url = result.content?.src?:"",
                name = result.title ?: "",
                author = result.author?.username ?: "",
                isFavorite = result.isFavourited ?: false,
                placeholderWidth = result.preview?.width ?: 0,
                placeholderHeight = result.preview?.height ?: 0,
                deviationid = result.deviationid ?: "" )
    }

    fun convertToImage(result : CollectionsAllResponse.Results) : Image {
        return Image(  preview_url = result.preview?.src?:"",
                content_url = result.content?.src?:"",
                name = result.title ?: "",
                author = result.author?.username ?: "",
                isFavorite = result.isFavourited ?: false,
                placeholderWidth = result.preview?.width ?: 0,
                placeholderHeight = result.preview?.height ?: 0,
                deviationid = result.deviationid ?: "" )
    }

}