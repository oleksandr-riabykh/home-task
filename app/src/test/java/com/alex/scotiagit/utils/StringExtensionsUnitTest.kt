package com.alex.scotiagit.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.text.ParseException

/**
 * Test string extensions
 */
class StringExtensionsUnitTest {

    @Test
    fun `If date format working successfully`() {
        val inputString = "2023-04-03T15:53:05Z"
        val expectedResult = "Apr 03 2023 03:53"

        // action
        val result = inputString.toDisplayDate()

        // check result
        assertEquals(expectedResult, result)
    }

    @Test(expected = ParseException::class)
    fun `If date format fail with ParseException`() {
        val inputString = "04/03/23T15:53:05Z"
        val expectedResult = "Apr 03 2023 03:53"

        // action
        val result = inputString.toDisplayDate()

        // check result
        assertNotEquals(expectedResult, result)
    }

    @Test
    fun `If date format fail`() {
        val inputString = "04-03-2023T15:53:05Z"
        val expectedResult = "Apr 03 2023 03:53"

        // action
        val result = inputString.toDisplayDate()

        // check result
        assertNotEquals(expectedResult, result)
    }
}