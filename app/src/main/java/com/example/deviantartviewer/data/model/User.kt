package com.example.deviantartviewer.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
        var userid   : String,
        var username : String,
        var usericon_url : String? = null,
        var usericon_uri : String? = null,
        var age : String? = null,
        var country : String? = null,
        var profilPageviews : Int,
        var userFavorites : Int,
        var userComments : Int,
        var profileComments : Int,
        var watchers : Int,
        var friends : Int
)