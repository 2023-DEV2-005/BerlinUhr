package com.bnpparibasfortis.usecase

import com.bnpparibasfortis.domain.BerlinClockConverter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TimeBerlinClock @Inject constructor(private val converter: BerlinClockConverter) {

    operator fun invoke() = flow {
        while (true) {
            emit(convert())
            delay(TIME_INTERVAL)
        }
    }

    private fun convert() = converter.from(LocalTime.now().format(DateTimeFormatter.ISO_TIME))

    companion object {
        const val TIME_INTERVAL = 1000L
    }
}