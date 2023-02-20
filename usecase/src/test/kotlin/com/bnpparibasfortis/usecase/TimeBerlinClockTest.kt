package com.bnpparibasfortis.usecase

import com.bnpparibasfortis.domain.BerlinClockConverter
import com.bnpparibasfortis.domain.model.BerlinClock.Companion.default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.jupiter.MockitoExtension
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_TIME

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
internal class TimeBerlinClockTest {

    @Mock
    private lateinit var clockConverter: BerlinClockConverter

    @Captor
    private lateinit var clockConverterCaptor: ArgumentCaptor<String>

    @InjectMocks
    private lateinit var usecase: TimeBerlinClock

    @Test
    fun `TimeBerlinClock should time in Berlin Clock format`() = runTest {
        openMocks(this) // running it before, outside this scope, mocking will fail

        val expectedClock = default()
        `when`(clockConverter.from(anyString())).thenReturn(expectedClock)

        val time = LocalTime.now()

        val result = usecase().take(3).toList()

        verify(clockConverter, times(3)).from(capture(clockConverterCaptor))
        expectThat(result).isEqualTo(List(3) { expectedClock })

        listOf(
            clockConverterCaptor.allValues[0].timeShortFormat() to time.shortFormat(),
            clockConverterCaptor.allValues[1].timeShortFormat() to time.plusSeconds(1).shortFormat(),
            clockConverterCaptor.allValues[1].timeShortFormat() to time.plusSeconds(2).shortFormat()
        ).map { (input, expected) ->
            dynamicTest("Converter should be called with $expected, was called with $input") {
                expectThat(input).isEqualTo(expected)
            }
        }
    }

    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
    private fun LocalTime.shortFormat() = format(ISO_TIME).timeShortFormat()
    private fun String.timeShortFormat() = split(".").first() // HH:mm:ss format
}