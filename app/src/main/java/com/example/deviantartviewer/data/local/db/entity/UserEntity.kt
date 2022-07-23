package com.example.deviantartviewer.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "userid")
        val userid: String,

        @ColumnInfo(name = "username")
        val username: String,

        @ColumnInfo(name = "usericon_url")
        val usericonUrl: String?,

        @ColumnInfo(name = "usericon_uri")
        val usericonUri: String?,

        @ColumnInfo(name = "age")
        val age: String?,

        @ColumnInfo(name = "country")
        val country: String?,

        @ColumnInfo(name = "profile_pageviews")
        val profilePageviews: Int,

        @ColumnInfo(name = "user_favorites")
        val userFavorites: Int,

        @ColumnInfo(name = "user_comments")
        val userComments: Int,

        @ColumnInfo(name = "profile_comments")
        val profileComments: Int,

        @ColumnInfo(name = "watchers")
        val watchers: Int,

        @ColumnInfo(name = "friends")
        val friends: Int

)