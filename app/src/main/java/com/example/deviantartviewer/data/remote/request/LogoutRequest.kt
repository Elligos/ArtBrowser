package com.example.deviantartviewer.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LogoutRequest (
    @Expose
    @SerializedName("token")
    private val token : String

//    @Expose
//    @SerializedName("revoke_refresh_only")
//    private val revoke_refresh_only : Boolean
)