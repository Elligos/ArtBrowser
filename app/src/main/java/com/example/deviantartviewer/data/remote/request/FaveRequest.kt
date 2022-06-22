package com.example.deviantartviewer.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FaveRequest  (
        @Expose
        @SerializedName("deviationid")
        private val deviationid : String,

        @Expose
        @SerializedName("access_token")
        private val access_token : String
)