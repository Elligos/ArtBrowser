package com.example.deviantartviewer.utils


import com.example.deviantartviewer.utils.common.Validator
import org.junit.Assert
import org.junit.Assert.assertThat
//import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class ValidatorTest( private val email: String?,
                     private val password : String?,
                     private val expectedResult: Boolean) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "with  {0} and {1} should return {2}")
        fun data(): List<Array<Any?>> {
            return listOf(
                    //Valid email and valid password
                    arrayOf("mail777@mail.com", "Strong_Password#7", true),
                    arrayOf("email@example.com", "Strong_Password#7", true),
                    arrayOf("firstname.lastname@example.com", "Strong_Password#7", true),
                    arrayOf("email@subdomain.example.com", "Strong_Password#7", true),
                    arrayOf("firstname+lastname@example.com", "Strong_Password#7", true),
                    arrayOf("email@123.123.123.123", "Strong_Password#7", true),
                    arrayOf("1234567890@example.com", "Strong_Password#7", true),
                    arrayOf("email@example-one.com", "Strong_Password#7", true),
                    arrayOf("_______@example.com", "Strong_Password#7", true),
                    arrayOf("email@example.name", "Strong_Password#7", true),
                    arrayOf("email@example.museum", "Strong_Password#7", true),
                    arrayOf("email@example.co.jp", "Strong_Password#7", true),
                    arrayOf("firstname-lastname@example.com", "Strong_Password#7", true),

                    // Invalid email and valid password
                    arrayOf("plainaddress", "Strong_Password#7", false),
                    arrayOf("#@%^%#\$@#\$@#.com", "Strong_Password#7", false),
                    arrayOf("@example.com", "Strong_Password#7", false),
                    arrayOf("Joe Smith <email@example.com>", "Strong_Password#7", false),
                    arrayOf("email.example.com", "Strong_Password#7", false),
                    arrayOf("email@example@example.com", "Strong_Password#7", false),
                    arrayOf("                    \n", "Strong_Password#7", false),
                    arrayOf("あいうえお@example.com", "Strong_Password#7", false),
                    arrayOf("email@example.com (Joe Smith)", "Strong_Password#7", false),
                    arrayOf("email@example", "Strong_Password#7", false),
                    arrayOf("email@-example.com", "Strong_Password#7", false),
                    arrayOf("email@example..com", "Strong_Password#7", false),

                    // Valid email and invalid password
                    arrayOf("mail777@mail,com", "invalidpassword", false),
                    arrayOf("mail777@mail.com", "888888888888888", false),
                    arrayOf("mail777@mail.com", "===============", false),
                    arrayOf("mail777@mail.com", "l", false),
                    arrayOf("mail777@mail.com", "#1Aa", false),
                    arrayOf("mail777@mail.com", "あいうえおあえおあ", false),

                    // Null values
                    arrayOf(null, "llll%strongPassword", false),
                    arrayOf("mail777@mail.com", null, false)
            )
        }
    }

        @Test
    fun `when validation`() {
        // given

        // when
        val emailValidation = Validator.isEmailValid(email)
        val passwordValidation = Validator.isPasswordValid(password)
        val validation = emailValidation && passwordValidation

        // then
        Assert.assertEquals(validation, expectedResult)
    }
}