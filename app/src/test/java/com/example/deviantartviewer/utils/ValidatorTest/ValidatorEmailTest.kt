package com.example.deviantartviewer.utils.ValidatorTest


import com.example.deviantartviewer.utils.common.Validator
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class ValidatorEmailTest( private val email: String?,
                          private val expectedResult: Boolean) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "with email: {0} should return {1}")
        fun data(): List<Array<Any?>> {
            return listOf(
                    //Valid email and valid password
                    arrayOf("mail777@mail.com",  true),
                    arrayOf("email@example.com", true),
                    arrayOf("firstname.lastname@example.com",  true),
                    arrayOf("email@subdomain.example.com",  true),
                    arrayOf("firstname+lastname@example.com",  true),
                    arrayOf("email@123.123.123.123",  true),
                    arrayOf("1234567890@example.com",  true),
                    arrayOf("email@example-one.com",  true),
                    arrayOf("_______@example.com",  true),
                    arrayOf("email@example.name",  true),
                    arrayOf("email@example.museum",  true),
                    arrayOf("email@example.co.jp",  true),
                    arrayOf("firstname-lastname@example.com",  true),

                    // Invalid email and valid password
                    arrayOf("plainaddress", false),
                    arrayOf("#@%^%#\$@#\$@#.com",  false),
                    arrayOf("@example.com",  false),
                    arrayOf("Joe Smith <email@example.com>",  false),
                    arrayOf("email.example.com",  false),
                    arrayOf("email@example@example.com", false),
                    arrayOf("                    \n", false),
                    arrayOf("あいうえお@example.com", false),
                    arrayOf("email@example.com (Joe Smith)",  false),
                    arrayOf("email@example", false),
                    arrayOf("email@-example.com",  false),
                    arrayOf("email@example..com",  false),
                    arrayOf("",  false),


                    // Null value
                    arrayOf(null, false),
            )
        }
    }

    @Test
    fun `when validation`() {
        // given

        // when
        val validation = Validator.isEmailValid(email)

        // then
        Assert.assertEquals(validation, expectedResult)
    }
}

