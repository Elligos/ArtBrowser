package com.example.deviantartviewer.data.remote

object Endpoints {
    const val AUTHORIZE = "https://www.deviantart.com/oauth2/authorize"
    const val TOKEN = "https://www.deviantart.com/oauth2/token"
    const val LOGOUT = "https://www.deviantart.com/oauth2/revoke"

    const val WHOAMI = "user/whoami"
    const val WHOIS = "user/whois"
    const val PROFILE = "user/profile/{username}"
    const val WATCHERS = "user/watchers/{username}"

    const val NEWEST = "browse/newest"

}