package com.example.deviantartviewer.utils.ValidatorTest



import com.example.deviantartviewer.utils.common.Validator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class ValidatorPasswordTest( private val password : String?,
                             private val expectedResult: Boolean) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "with password: {0} should return {1}")
        fun data(): List<Array<Any?>> {
            return listOf(
                    //Valid  password
                    arrayOf("Strong_Password#7", true),
                    arrayOf("Strong_Password7-@%[}+'!/#?:;,()~`.*=&{>]<_", true),
                    arrayOf("StrongPassword7[", true),



                    // Invalid password
                    arrayOf("invalidpassword", false),
                    arrayOf("888888888888888", false),
                    arrayOf("===============", false),
                    arrayOf("l", false),
                    arrayOf("#1Aa", false),
                    arrayOf("あいうえおあえおあ", false),
                    arrayOf("", false),

                    // Null value
                    arrayOf(null, false),
            )
        }
    }

    @Test
    fun `when validation`() {
        // given

        // when
        val validation = Validator.isPasswordValid(password)

        // then
        Assert.assertEquals(validation, expectedResult)
    }
}

