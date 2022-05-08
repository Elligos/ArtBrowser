package com.example.deviantartviewer.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @Expose
    @SerializedName("user"             )
    var user            : User?    = User(),

    @Expose
    @SerializedName("is_watching"      )
    var isWatching      : Boolean? = null,

    @Expose
    @SerializedName("profile_url"      )
    var profileUrl      : String?  = null,

    @Expose
    @SerializedName("user_is_artist"   )
    var userIsArtist    : Boolean? = null,

    @Expose
    @SerializedName("artist_level"     )
    var artistLevel     : String?  = null,

    @Expose
    @SerializedName("artist_specialty" )
    var artistSpecialty : String?  = null,

    @Expose
    @SerializedName("real_name"        )
    var realName        : String?  = null,

    @Expose
    @SerializedName("tagline"          )
    var tagline         : String?  = null,

    @Expose
    @SerializedName("countryid"        )
    var countryid       : Int?     = null,

    @Expose
    @SerializedName("country"          )
    var country         : String?  = null,

    @Expose
    @SerializedName("website"          )
    var website         : String?  = null,

    @Expose
    @SerializedName("bio"              )
    var bio             : String?  = null,

    @Expose
    @SerializedName("cover_photo"      )
    var coverPhoto      : String?  = null,

    @Expose
    @SerializedName("last_status"      )
    var lastStatus      : String?  = null,

    @Expose
    @SerializedName("stats"            )
    var stats           : Stats?   = Stats()
){

    data class User (
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
        var type     : String? = null,

        @Expose
        @SerializedName("details"  )
        var details  : Details? = Details(),

        @Expose
        @SerializedName("geo"      )
        var geo      : Geo?    = Geo(),

        @Expose
        @SerializedName("stats"    )
        var userStats    : UserStats?   = UserStats()
    )

    data class Details (

        @Expose
        @SerializedName("sex"      )
        var sex      : String? = null,

        @Expose
        @SerializedName("age"      )
        var age      : String? = null,

        @Expose
        @SerializedName("joindate" )
        var joindate : String? = null

    )

    data class Geo (
        @Expose
        @SerializedName("country"   )
        var country   : String? = null,

        @Expose
        @SerializedName("countryid" )
        var countryid : Int?    = null,

        @Expose
        @SerializedName("timezone"  )
        var timezone  : String? = null

    )

    data class UserStats (

        @SerializedName("watchers" ) var watchers : Int? = null,
        @SerializedName("friends"  ) var friends  : Int? = null

    )

    data class Stats (
        @Expose
        @SerializedName("user_deviations"   )
        var userDeviations   : Int? = null,

        @Expose
        @SerializedName("user_favourites"   )
        var userFavourites   : Int? = null,

        @Expose
        @SerializedName("user_comments"     )
        var userComments     : Int? = null,

        @Expose
        @SerializedName("profile_pageviews" )
        var profilePageviews : Int? = null,

        @Expose
        @SerializedName("profile_comments"  )
        var profileComments  : Int? = null

    )
}