package com.example.deviantartviewer.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogoutResponse(
        @Expose
        @SerializedName("success")
        var success   : Boolean = false
)
