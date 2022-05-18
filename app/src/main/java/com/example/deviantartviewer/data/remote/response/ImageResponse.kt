package com.example.deviantartviewer.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageResponse (
        @Expose
        @SerializedName("has_more"        )
        var hasMore        : Boolean?           = null,
        @Expose
        @SerializedName("next_offset"     )
        var nextOffset     : Int?               = null,
        @Expose
        @SerializedName("estimated_total" )
        var estimatedTotal : Int?               = null,
        @Expose
        @SerializedName("results"         )
        var results        : ArrayList<Results> = arrayListOf()
){


    data class Results (
            @Expose
            @SerializedName("deviationid"       )
            var deviationid      : String?           = null,
            @Expose
            @SerializedName("printid"           )
            var printid          : String?           = null,
            @Expose
            @SerializedName("url"               )
            var url              : String?           = null,
            @Expose
            @SerializedName("title"             )
            var title            : String?           = null,
            @Expose
            @SerializedName("category"          )
            var category         : String?           = null,
            @Expose
            @SerializedName("category_path"     )
            var categoryPath     : String?           = null,
            @Expose
            @SerializedName("is_favourited"     )
            var isFavourited     : Boolean?          = null,
            @Expose
            @SerializedName("is_deleted"        )
            var isDeleted        : Boolean?          = null,
            @Expose
            @SerializedName("is_published"      )
            var isPublished      : Boolean?          = null,
            @Expose
            @SerializedName("is_blocked"        )
            var isBlocked        : Boolean?          = null,
            @Expose
            @SerializedName("author"            )
            var author           : Author?           = Author(),
            @Expose
            @SerializedName("stats"             )
            var stats            : Stats?            = Stats(),
            @Expose
            @SerializedName("published_time"    )
            var publishedTime    : String?           = null,
            @Expose
            @SerializedName("allows_comments"   )
            var allowsComments   : Boolean?          = null,
            @Expose
            @SerializedName("preview"           )
            var preview          : Preview?          = Preview(),
            @SerializedName("content"           )
            @Expose
            var content          : Content?          = Content(),
            @Expose
            @SerializedName("thumbs"            )
            var thumbs           : ArrayList<Thumbs> = arrayListOf(),
            @Expose
            @SerializedName("is_mature"         )
            var isMature         : Boolean?          = null,
            @Expose
            @SerializedName("is_downloadable"   )
            var isDownloadable   : Boolean?          = null,
            @Expose
            @SerializedName("download_filesize" )
            var downloadFilesize : Int?              = null

    )

    data class Author (
            @Expose
            @SerializedName("userid"        )
            var userid       : String?  = null,
            @Expose
            @SerializedName("username"      )
            var username     : String?  = null,
            @Expose
            @SerializedName("usericon"      )
            var usericon     : String?  = null,
            @Expose
            @SerializedName("type"          )
            var type         : String?  = null,
            @Expose
            @SerializedName("is_subscribed" )
            var isSubscribed : Boolean? = null

    )

    data class Stats (
            @Expose
            @SerializedName("comments"   )
            var comments   : Int? = null,
            @Expose
            @SerializedName("favourites" )
            var favourites : Int? = null
    )

    data class Preview (
            @Expose
            @SerializedName("src"          )
            var src          : String?  = null,
            @Expose
            @SerializedName("height"       )
            var height       : Int?     = null,
            @Expose
            @SerializedName("width"        )
            var width        : Int?     = null,
            @Expose
            @SerializedName("transparency" )
            var transparency : Boolean? = null

    )

    data class Content (
            @Expose
            @SerializedName("src"          )
            var src          : String?  = null,
            @Expose
            @SerializedName("height"       )
            var height       : Int?     = null,
            @Expose
            @SerializedName("width"        )
            var width        : Int?     = null,
            @Expose
            @SerializedName("transparency" )
            var transparency : Boolean? = null,
            @Expose
            @SerializedName("filesize"     )
            var filesize     : Int?     = null

    )

    data class Thumbs (
            @Expose
            @SerializedName("src"          )
            var src          : String?  = null,
            @Expose
            @SerializedName("height"       )
            var height       : Int?     = null,
            @Expose
            @SerializedName("width"        )
            var width        : Int?     = null,
            @Expose
            @SerializedName("transparency" )
            var transparency : Boolean? = null

    )

}