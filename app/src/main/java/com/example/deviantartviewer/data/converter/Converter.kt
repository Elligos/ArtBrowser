package com.example.deviantartviewer.data.converter

import com.example.deviantartviewer.data.local.db.entity.UserEntity
import com.example.deviantartviewer.data.model.Image
import com.example.deviantartviewer.data.model.User
import com.example.deviantartviewer.data.remote.response.CollectionsAllResponse
import com.example.deviantartviewer.data.remote.response.ImageResponse
import com.example.deviantartviewer.data.remote.response.ProfileResponse

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
        return Image(   preview_url = result.preview?.src?:"",
                        content_url = result.content?.src?:"",
                        name = result.title ?: "",
                        author = result.author?.username ?: "",
                        isFavorite = result.isFavourited ?: false,
                        placeholderWidth = result.preview?.width ?: 0,
                        placeholderHeight = result.preview?.height ?: 0,
                        deviationid = result.deviationid ?: "" )
    }

    fun convertToUserEntity(user : User) : UserEntity{
        return UserEntity(  userid=user.userid,
                            username=user.username,
                            usericonUrl=user.usericon_url,
                            usericonUri=user.usericon_uri,
                            age=user.age,
                            country=user.country,
                            profilePageviews=user.profilPageviews,
                            userFavorites=user.userFavorites,
                            userComments=user.userComments,
                            profileComments=user.profileComments,
                            watchers=user.watchers,
                            friends=user.friends    )
    }

    fun convertToUser(entity : UserEntity) : User{
        return User(userid=entity.userid,
                    username=entity.username,
                    usericon_url=entity.usericonUrl,
                    usericon_uri=entity.usericonUri,
                    age=entity.age,
                    country=entity.country,
                    profilPageviews=entity.profilePageviews,
                    userFavorites=entity.userFavorites,
                    userComments=entity.userComments,
                    profileComments=entity.profileComments,
                    watchers=entity.watchers,
                    friends=entity.friends )
    }

    fun convertToUser(data : ProfileResponse, usericon_uri : String = "") : User{
        val big_usericon_url = convertToBigAvatarUrl(data.user?.usericon?:"")
        return User(userid=data.user?.userid?:"",
                username=data.user?.username?:"",
                usericon_url=big_usericon_url,
                usericon_uri=usericon_uri,
                age=data.user?.details?.age,
                country=data.user?.geo?.country,
                profilPageviews=data.stats?.profilePageviews?:0,
                userFavorites=data.stats?.userFavourites?:0,
                userComments=data.stats?.userComments?:0,
                profileComments=data.stats?.profileComments?:0,
                watchers=data.user?.userStats?.watchers?:0,
                friends=data.user?.userStats?.friends?:0 )
    }

    fun convertToBigAvatarUrl(avatarUrl : String) : String{
        return avatarUrl.replace("https://a.deviantart.net/avatars",
                "https://a.deviantart.net/avatars-big")
    }

}