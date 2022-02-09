package com.example.deviantartviewer.utils.ValidatorTest


import com.example.deviantartviewer.utils.common.Validator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized




@RunWith(Parameterized::class)
class ValidatorUsernameTest( private val username: String?,
                             private val expectedResult: Boolean ) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "with username {0} should return {1}")
        fun data(): List<Array<Any?>> {
            return listOf(
                    //Valid username
                    arrayOf("A",  true),
                    arrayOf("a",  true),
                    arrayOf("JustUser",  true),
                    arrayOf("Just4User",  true),
                    arrayOf("JustUser1977",  true),
                    arrayOf("1977JustUser",  true),

                    // Invalid username
                    arrayOf("1977",  false),
                    arrayOf("JustUser#",  false),
                    arrayOf("Just_User",  false),
                    arrayOf("Just User",  false),
                    arrayOf("UsernameWithMoreThanTwentyCharacters",  false),
                    arrayOf("",  false),

                    // Null value
                    arrayOf(null,  false)

            )
        }
    }

    @Test
    fun `when validation`() {
        // given

        // when
        val validation = Validator.isUsernameValid(username)

        // then
        Assert.assertEquals(validation, expectedResult)
    }
}