package com.example.poapp

import org.junit.Assert.assertEquals
import org.junit.Test


class FormatDateTest {

    @Test
    fun fullNumbers() {
        val result = Utils.formatDate(2000, 9, 10)
        assertEquals(result, "2000-10-10")
    }

    @Test
    fun singleMonth() {
        val result = Utils.formatDate(2000, 8, 10)
        assertEquals(result, "2000-09-10")
    }

    @Test
    fun singleDay() {
        val result = Utils.formatDate(2000, 9, 1)
        assertEquals(result, "2000-10-01")
    }

    @Test
    fun bothSingle() {
        val result = Utils.formatDate(2020, 8, 1)
        assertEquals(result, "2020-09-01")
    }

    @Test
    fun leapYearFebruary() {
        val result = Utils.formatDate(2020, 1, 29)
        assertEquals(result, "2020-02-29")
    }

    @Test(expected = IllegalArgumentException::class)
    fun notLeapYearFebruary() {
        Utils.formatDate(2021, 1, 29)
    }

    @Test(expected = IllegalArgumentException::class)
    fun incorrectDay() {
        Utils.formatDate(2021, 1, 30)
    }

    @Test(expected = IllegalArgumentException::class)
    fun incorrectMonthTooLow() {
        Utils.formatDate(2021, -2, 30)
    }

    @Test(expected = IllegalArgumentException::class)
    fun incorrectDayTooLow() {
        Utils.formatDate(2021, 1, -2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun incorrectYearTooLow() {
        Utils.formatDate(1998, -2, 30)
    }
}