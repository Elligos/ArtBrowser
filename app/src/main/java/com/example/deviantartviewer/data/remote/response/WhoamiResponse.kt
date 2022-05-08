package com.example.deviantartviewer.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WhoamiResponse (
        @Expose
        @SerializedName("userid"   )
        var userid   : String? = null,

        @Expose
        @SerializedName("username" )
        var username : String? = null,

        @Expose
        @SerializedName("usericon" )
        var usericon : String? = null,

        @Expose
        @SerializedName("type"     )
        var type     : String? = null
)