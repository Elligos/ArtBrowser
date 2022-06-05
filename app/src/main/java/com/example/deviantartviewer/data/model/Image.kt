package com.example.deviantartviewer.data.model

data class Image(
        var url: String,
        val name: String,
        val author: String = "",
        var isFavorited: Boolean = false,
        val placeholderWidth: Int = -1,
        val placeholderHeight: Int = -1,
)
