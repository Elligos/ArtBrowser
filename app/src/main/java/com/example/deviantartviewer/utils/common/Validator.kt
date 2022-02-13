package com.example.deviantartviewer.utils.common

import android.util.Patterns
import com.example.deviantartviewer.R
import java.util.*
import java.util.regex.Pattern

object Validator {



////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  Password regex pattern description /////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
//    ^                 # start-of-string
//    (?=.*[0-9])       # a digit must occur at least once
//    (?=.*[a-z])       # a lower case letter must occur at least once
//    (?=.*[A-Z])       # an upper case letter must occur at least once
//    (?=.*[a-zA-Z])    # any letter must occur at least once
//    (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your
//                      # special characters
//    (?!.*[@#$%^&+=])  # no special allowed in the entire string
//    (?=\\S+$)         # no whitespace allowed in the entire string
//    .{6,}             # anything, at least six places though
//    $                 # end-of-string
////////////////////////////////////////////////////////////////////////////////////////////////////

     const val SPECIAL_CHARACTERS = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
     const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$"
     const val EMAIL_REGEX =  "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    const val USERNAME_REGEX = "^(?=.*[a-zA-Z])(?!.*[$SPECIAL_CHARACTERS])(?=\\S+$).{1,20}$"


    fun isEmailValid(email : String?) : Boolean{
        if(email.isNullOrBlank()) return false
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches()
    }

    fun isPasswordValid(password : String?) : Boolean{
        if(password.isNullOrBlank()) return false
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches()
    }

    fun isUsernameValid(username : String?): Boolean{
        if(username.isNullOrBlank()) return false
        return Pattern.compile(USERNAME_REGEX).matcher(username).matches()
    }
}
