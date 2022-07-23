package com.example.deviantartviewer.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        const val KEY_USER_ID = "PREF_KEY_USER_ID"
        const val KEY_USER_OUTDATED = "PREF_KEY_USER_OUTDATED"
    }

    fun setUserInfoOutdated(isOutdated : Boolean) =
            prefs.edit().putBoolean(KEY_USER_OUTDATED, isOutdated).apply()

    fun getUserInfoOutdated() : Boolean =
            prefs.getBoolean(KEY_USER_OUTDATED, true)

    fun getUserId(): String =
            prefs.getString(KEY_USER_ID, "")?:""

    fun setUserId(userId: String) =
            prefs.edit().putString(KEY_USER_ID, userId).apply()

    fun removeUserId() =
            prefs.edit().remove(KEY_USER_ID).apply()


}