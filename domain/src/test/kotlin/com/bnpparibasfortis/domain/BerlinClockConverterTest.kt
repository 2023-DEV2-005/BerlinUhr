package com.bnpparibasfortis.domain

import com.bnpparibasfortis.domain.model.Light.OFF
import com.bnpparibasfortis.domain.model.Light.YELLOW
import com.bnpparibasfortis.domain.model.Light.RED
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BerlinClockConverterTest {

    private val berlinClockConverter = BerlinClockConverter()

    @TestFactory
    fun `Test second conversion`() = listOf(
        "00:00:00" to YELLOW,
        "00:00:01" to OFF,
        "00:00:02" to YELLOW,
        "00:00:59" to OFF,
        "00:00:38" to YELLOW
    ).map { (input, expected) ->
        dynamicTest("$input should be converted to $expected second light") {
            expectThat(convert(input).seconds).isEqualTo(expected)
        }
    }

    @TestFactory
    fun `Test hour's top row conversion`() = listOf(
        "00:00:00" to listOf(OFF, OFF, OFF, OFF),
        "04:59:59" to listOf(OFF, OFF, OFF, OFF),
        "05:00:00" to listOf(RED, OFF, OFF, OFF),
        "09:59:59" to listOf(RED, OFF, OFF, OFF),
        "10:00:00" to listOf(RED, RED, OFF, OFF),
        "14:59:59" to listOf(RED, RED, OFF, OFF),
        "15:00:00" to listOf(RED, RED, RED, OFF),
        "19:59:59" to listOf(RED, RED, RED, OFF),
        "20:00:00" to listOf(RED, RED, RED, RED),
        "23:59:59" to listOf(RED, RED, RED, RED),
    ).map { (input, expected) ->
        dynamicTest("$input should be converted to $expected as top row hour lights") {
            expectThat(convert(input).hourTopRow).isEqualTo(expected)
        }
    }

    @TestFactory
    fun `Test hour's bottom row conversion`() = listOf(
        "00:00:00" to listOf(OFF, OFF, OFF, OFF),
        "00:59:59" to listOf(OFF, OFF, OFF, OFF),
        "01:00:00" to listOf(RED, OFF, OFF, OFF),
        "01:59:00" to listOf(RED, OFF, OFF, OFF),
        "02:00:00" to listOf(RED, RED, OFF, OFF),
        "02:59:00" to listOf(RED, RED, OFF, OFF),
        "03:00:00" to listOf(RED, RED, RED, OFF),
        "03:59:00" to listOf(RED, RED, RED, OFF),
        "04:00:00" to listOf(RED, RED, RED, RED),
        "04:59:59" to listOf(RED, RED, RED, RED),
        "05:00:00" to listOf(OFF, OFF, OFF, OFF),
        "05:59:00" to listOf(OFF, OFF, OFF, OFF),
        "06:00:00" to listOf(RED, OFF, OFF, OFF),
        "06:59:00" to listOf(RED, OFF, OFF, OFF),
        "10:00:00" to listOf(OFF, OFF, OFF, OFF),
        "15:00:00" to listOf(OFF, OFF, OFF, OFF),
        "20:00:00" to listOf(OFF, OFF, OFF, OFF),
        "23:59:59" to listOf(RED, RED, RED, OFF),
    ).map { (input, expected) ->
        dynamicTest("$input should be converted to $expected as bottom row hour lights") {
            expectThat(convert(input).hourBottomRow).isEqualTo(expected)
        }
    }

    @TestFactory
    fun `Test minute's top row conversion`() = listOf(
        "00:04:00" to listOf(OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "01:04:59" to listOf(OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "02:05:00" to listOf(YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "03:09:59" to listOf(YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "04:10:00" to listOf(YELLOW, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "05:14:59" to listOf(YELLOW, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "06:15:00" to listOf(YELLOW, YELLOW, RED, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "07:19:59" to listOf(YELLOW, YELLOW, RED, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "08:20:00" to listOf(YELLOW, YELLOW, RED, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "09:24:59" to listOf(YELLOW, YELLOW, RED, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF, OFF),
        "10:25:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF),
        "14:29:59" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, OFF, OFF, OFF, OFF, OFF, OFF),
        "15:30:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, OFF, OFF, OFF, OFF, OFF),
        "16:35:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, OFF, OFF, OFF, OFF),
        "17:40:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, OFF, OFF, OFF),
        "18:45:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, OFF, OFF),
        "19:50:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, OFF),
        "20:55:00" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW),
        "23:59:59" to listOf(YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW),
    ).map { (input, expected) ->
        dynamicTest("$input should be converted to $expected as top row minute lights") {
            expectThat(convert(input).minuteTopRow).isEqualTo(expected)
        }
    }

    @TestFactory
    fun `Test minute's bottom row conversion`() = listOf(
        "00:00:00" to listOf(OFF, OFF, OFF, OFF),
        "01:00:59" to listOf(OFF, OFF, OFF, OFF),
        "02:01:00" to listOf(YELLOW, OFF, OFF, OFF),
        "03:01:59" to listOf(YELLOW, OFF, OFF, OFF),
        "04:02:00" to listOf(YELLOW, YELLOW, OFF, OFF),
        "05:02:59" to listOf(YELLOW, YELLOW, OFF, OFF),
        "06:03:00" to listOf(YELLOW, YELLOW, YELLOW, OFF),
        "07:03:59" to listOf(YELLOW, YELLOW, YELLOW, OFF),
        "08:04:00" to listOf(YELLOW, YELLOW, YELLOW, YELLOW),
        "09:04:59" to listOf(YELLOW, YELLOW, YELLOW, YELLOW),
        "10:05:00" to listOf(OFF, OFF, OFF, OFF),
        "11:06:00" to listOf(YELLOW, OFF, OFF, OFF),
        "12:07:00" to listOf(YELLOW, YELLOW, OFF, OFF),
        "13:08:00" to listOf(YELLOW, YELLOW, YELLOW, OFF),
        "14:09:00" to listOf(YELLOW, YELLOW, YELLOW, YELLOW),
        "15:10:00" to listOf(OFF, OFF, OFF, OFF),
        "16:15:00" to listOf(OFF, OFF, OFF, OFF),
        "17:20:00" to listOf(OFF, OFF, OFF, OFF),
        "18:25:59" to listOf(OFF, OFF, OFF, OFF),
        "19:30:59" to listOf(OFF, OFF, OFF, OFF),
        "20:35:59" to listOf(OFF, OFF, OFF, OFF),
        "21:40:59" to listOf(OFF, OFF, OFF, OFF),
        "22:45:59" to listOf(OFF, OFF, OFF, OFF),
        "23:50:59" to listOf(OFF, OFF, OFF, OFF),
        "23:55:59" to listOf(OFF, OFF, OFF, OFF),
        "23:59:59" to listOf(YELLOW, YELLOW, YELLOW, YELLOW),
    ).map { (input, expected) ->
        dynamicTest("$input should be converted to $expected as bottom row minute lights") {
            expectThat(convert(input).minuteBottomRow).isEqualTo(expected)
        }
    }

    private fun convert(time: String) = berlinClockConverter.from(time)
}